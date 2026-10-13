#!/usr/bin/env bash
#
# 배포 서버(Oracle/EC2 등 Ubuntu)에서 직접 실행하는 pull 기반 배포 스크립트.
# GitHub 에서 cms_back / cms_front 를 pull → 빌드 → /opt/cmsweb, /var/www/cmsweb 에 반영 → 재시작.
#
# 설치(1회):
#   sudo mkdir -p /opt/cmsweb/src && sudo chown "$USER" /opt/cmsweb/src
#   git clone https://github.com/CMSWebSite/cms_back.git  /opt/cmsweb/src/cms_back
#   git clone https://github.com/CMSWebSite/cms_front.git /opt/cmsweb/src/cms_front
#   sudo cp /opt/cmsweb/src/cms_back/deploy/deploy-on-server.sh /opt/cmsweb/deploy.sh && sudo chmod +x /opt/cmsweb/deploy.sh
#
# 사용법:
#   /opt/cmsweb/deploy.sh                 # 두 저장소 모두 현재 체크아웃된 브랜치 pull + 배포
#   BRANCH=main /opt/cmsweb/deploy.sh     # 특정 브랜치로 전환 후 배포
#   ONLY=front /opt/cmsweb/deploy.sh      # 프론트만 (ONLY=back 이면 백엔드만)
#
# 사전 조건:
#   - JDK 21(openjdk-21-jdk-headless), Node 20.19+ (또는 22.12+), git 설치
#   - /etc/cmsweb.env, systemd cmsweb.service, nginx 설정은 기존과 동일하게 설치되어 있음
#   - 메모리 2GB 미만이면 swap 을 먼저 추가할 것 (Gradle + Vite 빌드가 OOM 날 수 있음)

set -euo pipefail

SRC_DIR="${SRC_DIR:-/opt/cmsweb/src}"
BRANCH="${BRANCH:-}"
ONLY="${ONLY:-all}"
JAR_DEST="/opt/cmsweb/cms_back.jar"
WEB_DEST="/var/www/cmsweb"

log() { echo; echo "==> $*"; }

pull_repo() {
    local dir="$1"
    cd "$dir"
    if [ -n "$BRANCH" ]; then
        git fetch origin
        git checkout "$BRANCH"
    fi
    local cur
    cur="$(git branch --show-current)"
    git pull --ff-only origin "$cur"
    echo "   $(basename "$dir") @ $cur $(git rev-parse --short HEAD)"
}

# 메모리 경고
MEM_MB=$(awk '/MemAvailable/ {printf "%d", $2/1024}' /proc/meminfo)
SWAP_MB=$(awk '/SwapTotal/ {printf "%d", $2/1024}' /proc/meminfo)
if [ $((MEM_MB + SWAP_MB)) -lt 2000 ]; then
    echo "경고: 사용 가능 메모리+swap 이 ${MEM_MB}+${SWAP_MB}MB 입니다. 빌드 중 OOM 가능성이 있으니 swap 추가를 권장합니다."
fi

if [ "$ONLY" != "front" ]; then
    log "[back 1/3] cms_back pull"
    pull_repo "$SRC_DIR/cms_back"

    log "[back 2/3] Gradle bootJar"
    cd "$SRC_DIR/cms_back"
    chmod +x gradlew
    # 서버 빌드 시 Gradle 데몬/메모리 사용 억제
    ./gradlew bootJar -x test --no-daemon -q -Dorg.gradle.jvmargs="-Xmx768m"
    JAR_PATH="$(ls build/libs/*-SNAPSHOT.jar | head -n 1)"

    log "[back 3/3] jar 교체 + cmsweb 재시작"
    sudo install -o cmsweb -g cmsweb -m 0644 "$JAR_PATH" "$JAR_DEST"
    sudo systemctl restart cmsweb
    sleep 3
    sudo systemctl is-active cmsweb >/dev/null || { echo "cmsweb 기동 실패"; sudo tail -n 40 /var/log/cmsweb/stderr.log; exit 1; }
fi

if [ "$ONLY" != "back" ]; then
    log "[front 1/3] cms_front pull"
    pull_repo "$SRC_DIR/cms_front"

    log "[front 2/3] npm ci + vite build"
    cd "$SRC_DIR/cms_front"
    npm ci --no-audit --no-fund
    npm run build

    log "[front 3/3] /var/www/cmsweb 교체 + nginx reload"
    sudo rm -rf "${WEB_DEST:?}"/*
    sudo cp -r dist/. "$WEB_DEST"/
    sudo chown -R www-data:www-data "$WEB_DEST"
    sudo nginx -t && sudo systemctl reload nginx
fi

log "헬스체크"
for i in 1 2 3 4 5 6; do
    if curl -fsS http://127.0.0.1/actuator/health >/dev/null 2>&1; then
        curl -s http://127.0.0.1/actuator/health; echo
        echo "배포 완료."
        exit 0
    fi
    sleep 5
done
echo "헬스체크 실패: sudo journalctl -u cmsweb -n 50 또는 /var/log/cmsweb/stderr.log 확인"
exit 1
