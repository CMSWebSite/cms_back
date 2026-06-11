# cms_back — CMS 연구실 웹사이트 백엔드

연구실 웹사이트의 **서버(API)** 프로젝트입니다. 회원/인증, 관리자 대시보드, 연구·논문·갤러리 등
모든 데이터를 처리하고 DB에 저장합니다.

- **기술 스택**: Java 21 · Spring Boot 3.5 · Spring Security(JWT) · JPA/Hibernate · Flyway · MySQL 8.0
- **짝 프로젝트**: 화면(UI)은 [`cms_front`](../cms_front) 가 담당합니다. 둘을 함께 띄워야 사이트가 동작합니다.

> 처음이라면 아래 **사전 준비 → 빠른 시작** 순서대로 그대로 따라 하면 됩니다.
> 명령은 Windows **PowerShell** 기준이며, 프로젝트 폴더(`cms_back`) 안에서 실행합니다.

---

## 1. 사전 준비 (한 번만)

| 필요한 것 | 용도 | 설치 | 설치 확인 명령 |
|---|---|---|---|
| **JDK 21** | 백엔드 실행 | [Adoptium Temurin 21](https://adoptium.net/temurin/releases/?version=21) | `java -version` → `21.x` |
| **MySQL 8.0** | 데이터 저장 | [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) | `mysql --version` |
| **Git** | 소스 내려받기 | [git-scm.com](https://git-scm.com/) | `git --version` |

> **Gradle은 따로 설치하지 않습니다.** 프로젝트에 포함된 `gradlew`(Gradle Wrapper)가 알맞은 버전을
> 자동으로 받아 씁니다. 그래서 항상 `./gradlew ...` 형태로 실행합니다.

설치 확인 (PowerShell에 그대로 입력):
```powershell
java -version
mysql --version
```

> MySQL 설치 시 정한 **root 비밀번호**를 꼭 기억해 두세요. 아래 2-2 단계에서 씁니다.

---

## 2. 빠른 시작 (4단계)

### 2-1. MySQL 실행 (localhost:3306)

MySQL을 설치하면 보통 **localhost의 3306 포트**에서 자동으로 켜집니다(Windows 서비스). 꺼져 있다면 켭니다.

```powershell
# MySQL 서비스 시작 (서비스 이름은 보통 MySQL80)
net start MySQL80
```

> 데이터베이스(`cms_db`)는 **따로 만들지 않아도 됩니다.** 접속 URL에 `createDatabaseIfNotExist=true`
> 옵션이 있어, 백엔드가 처음 켜질 때 `cms_db`가 없으면 자동으로 만듭니다.
> (직접 만들고 싶다면: `mysql -u root -p` 접속 후 `CREATE DATABASE cms_db;`)

### 2-2. DB 비밀번호 설정 ⚠️ (가장 중요)

백엔드가 MySQL에 접속하려면 **내 MySQL root 비밀번호**를 알려줘야 합니다.
아래 한 파일만 만들면 됩니다. (자세한 설명은 **3번 항목** 참고)

```powershell
# 템플릿 복사
copy src\main\resources\application-local.properties.example src\main\resources\application-local.properties
```
그다음 `src\main\resources\application-local.properties` 파일을 열어 **본인 비밀번호 한 줄**만 적습니다:
```properties
spring.datasource.password=내MySQL비밀번호
```
- 이 파일은 **자동으로 적용**되며(추가 옵션 불필요), `.gitignore` 대상이라 **커밋되지 않습니다**(비밀번호 노출 없음).
- MySQL 계정이 `root`가 아니라면 사용자 이름도 같이 적습니다: `spring.datasource.username=계정명`

### 2-3. 백엔드 실행

```powershell
./gradlew bootRun
```
처음엔 의존성을 받느라 1~2분 걸립니다. 콘솔에 **`Started CmsWebApplication`** 이 보이면 성공입니다.

- 서버 주소: **http://localhost:8080**
- 헬스체크로 확인: 브라우저에서 http://localhost:8080/actuator/health → `{"status":"UP"}`

> 기동할 때 **Flyway**가 DB 테이블을 자동으로 맞추고, JPA는 스키마가 코드(엔티티)와 일치하는지
> **검증(validate)만** 합니다. 즉 테이블을 손으로 만들 필요가 없습니다.

### 2-4. 종료

실행 중인 터미널에서 `Ctrl + C`.

---

## 3. DB 설정 — 어느 파일에서 어떻게 고치나 (상세)

DB 접속 정보는 **두 파일**로 나뉘어 있습니다. 역할을 구분하면 헷갈리지 않습니다.

### (A) 기본값 파일 — `src/main/resources/application.properties`

저장소에 커밋되는 **공용 기본 설정**입니다. DB 관련 핵심 3줄:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cms_db?useSSL=false&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=localdev      # ← 더미값(가짜). 실제 비번은 (B)에서 덮어씀
```

| 항목 | 의미 | 바꿀 때 |
|---|---|---|
| `url` 의 `localhost:3306` | DB 서버 주소/포트 | MySQL을 다른 포트로 띄웠다면 여기 숫자를 변경 |
| `url` 의 `/cms_db` | 사용할 데이터베이스 이름 | 다른 DB명을 쓰려면 변경 |
| `username` | MySQL 계정 | 보통 `root` |
| `password` | **여기엔 진짜 비번을 넣지 마세요** | 실제 비번은 (B) 파일에 |

> 💡 `password`에 더미값(`localdev`)을 둔 이유: 이 파일은 git에 커밋되므로 진짜 비밀번호를 적으면
> 모두에게 공유·노출됩니다. 그래서 **개인 비밀번호는 (B) 파일에 따로** 둡니다.

### (B) 개인 비밀번호 파일 — `src/main/resources/application-local.properties`

**커밋되지 않는(.gitignore)** 개인 전용 파일입니다. 여기에 적은 값이 (A)의 기본값을 **덮어씁니다.**
`application.properties` 의 `spring.config.import=optional:application-local.properties` 설정 덕분에
`./gradlew bootRun` 만 해도 **자동으로 적용**됩니다(프로필 옵션 불필요).

```properties
# application-local.properties — 본인 PC에만 존재. git에 안 올라감.
spring.datasource.password=내MySQL비밀번호
# (계정이 root가 아니면) spring.datasource.username=내계정
# (포트/DB명이 다르면) spring.datasource.url=jdbc:mysql://localhost:3306/cms_db?...
```

### 요약 — 어떤 경우에 무엇을 고치나

| 하고 싶은 것 | 고칠 파일 | 방법 |
|---|---|---|
| **내 MySQL 비밀번호 알려주기** (대부분의 경우) | `application-local.properties` | `spring.datasource.password=...` |
| MySQL 포트/계정/DB명 변경 | `application-local.properties` | 해당 줄을 덮어쓰기 |
| 팀 공통 기본값 변경(포트 표준 등) | `application.properties` | 단, 비밀번호는 절대 넣지 말 것 |

> 운영(배포) DB 설정은 저장소가 아니라 서버의 환경변수(`cmsweb.env`)로 주입합니다.
> 자세한 건 저장소 루트의 `deploy/AWS_DEPLOY.md` 참고.

---

## 4. 화면까지 함께 보려면

이 백엔드는 API만 제공합니다. 실제 사이트 화면은 [`cms_front`](../cms_front)를 함께 띄워야 합니다.
**터미널 2개**를 쓰세요.

1. (터미널 A) 여기 `cms_back` → 위 2단계로 백엔드 실행 (8080)
2. (터미널 B) `cms_front` → `npm run dev` 로 프런트 실행 (5173)
3. 브라우저에서 **http://localhost:5173** 접속

프런트가 `/api` 요청을 자동으로 8080 백엔드로 넘겨주므로(프록시), **백엔드를 먼저 켜는 것**이 좋습니다.

---

## 5. 자주 쓰는 명령

```powershell
./gradlew bootRun        # 개발 실행
./gradlew test           # 테스트 (Docker 필요 — MySQL 컨테이너로 실제 검증)
./gradlew build          # 빌드 + 테스트
./gradlew bootJar        # 배포용 단일 jar 생성 (build/libs/*.jar)
```

> `./gradlew` 가 PowerShell에서 안 먹히면 `.\gradlew.bat` 로 실행하세요.

---

## 6. 문제 해결

| 증상 | 원인 / 해결 |
|---|---|
| `Access denied for user 'root'` | DB 비밀번호 불일치. **2-2 / 3-(B)** 대로 `application-local.properties`에 본인 비밀번호 지정. |
| `Communications link failure` / `Connection refused` | MySQL이 안 떠 있음. `net start MySQL80` 으로 켜고, 3306 포트가 맞는지 확인. |
| `Unknown database 'cms_db'` | URL의 `createDatabaseIfNotExist=true` 가 지워졌거나 권한 부족. `CREATE DATABASE cms_db;` 직접 실행. |
| `Port 8080 ... already in use` | 8080을 다른 프로그램이 점유. 기존 실행 종료 후 재시도. |
| `java: command not found` / 버전 다름 | JDK 21 미설치/경로 문제. `java -version`으로 21인지 확인. |
| 기동 중 `Schema-validation: missing ...` | 스키마와 코드 불일치. 최신 코드를 받았는지 확인. 그래도 나면 팀에 공유. |

---

## 7. 참고 — Docker로 DB 띄우기 (선택)

MySQL을 직접 설치하지 않고 Docker로 띄우고 싶다면, 같은 설정의 컨테이너 정의(`docker-compose.dev.yml`)가
포함되어 있습니다. 이 경우 비밀번호가 `localdev` 로 맞춰져 있어 **2-2 단계가 필요 없습니다.**

```powershell
docker compose -f docker-compose.dev.yml up -d     # 시작 (localhost:3306)
docker compose -f docker-compose.dev.yml down       # 종료(데이터 유지)
docker compose -f docker-compose.dev.yml down -v     # 데이터까지 초기화
```

> 단, 이미 로컬에 MySQL이 3306에서 돌고 있으면 포트 충돌이 납니다. 둘 중 하나만 쓰세요.

---

## 8. 더 보기

- 배포 / 운영 절차: 저장소 루트 `deploy/AWS_DEPLOY.md`
- 프런트엔드: [`cms_front/README.md`](../cms_front/README.md)
