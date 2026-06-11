# cms_back

CMS 연구실 웹사이트 백엔드 (Spring Boot 3.5 / Java 21 / MySQL 8.0 / Flyway).

## 로컬 개발 셋업

### 1. 로컬 DB 띄우기 (docker)

운영 RDS와 동일한 MySQL 8.0을 로컬 컨테이너로 띄운다:

```bash
docker compose -f docker-compose.dev.yml up -d
```

- DB: `cms_db`, 계정: `root` / `localdev` (로컬 전용 더미값)
- 종료(데이터 유지): `docker compose -f docker-compose.dev.yml down`
- 데이터까지 삭제: `docker compose -f docker-compose.dev.yml down -v`

### 2. 백엔드 실행

```bash
./gradlew bootRun
```

기동 시 Flyway가 `src/main/resources/db/migration/`의 마이그레이션을 적용하고,
JPA는 `ddl-auto=validate`로 스키마-엔티티 일치만 검증한다.

> `application.properties`(dev 프로파일)의 DB 비밀번호·JWT 시크릿은 **로컬 전용 더미값**이다.
> 운영 시크릿은 저장소에 두지 않고 서버의 `cmsweb.env`(systemd `EnvironmentFile`)로 주입한다.

### 3. 개인 환경값으로 덮어쓰기 (선택)

개인 DB 비밀번호나 관리자 부트스트랩 이메일이 필요하면:

```bash
cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
# 값 수정 후
./gradlew bootRun --args='--spring.profiles.active=local'
```

`application-local.properties`는 `.gitignore` 대상이라 커밋되지 않는다.

## 테스트

```bash
./gradlew test
```

Testcontainers가 MySQL 8.0 컨테이너를 자동으로 띄워 Flyway 마이그레이션 + `validate`를
실제 DB에서 검증한다. **실행에 Docker가 필요하다.**

## 배포

운영 배포 절차는 저장소 루트의 `deploy/AWS_DEPLOY.md` 참고.
