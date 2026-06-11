package cms_back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * 컨텍스트 로드 통합 테스트.
 *
 * 운영 RDS와 동일한 MySQL 8.0 컨테이너를 띄워, 빈 DB에 Flyway가 V1__init.sql 을 적용하고
 * ddl-auto=validate 로 엔티티 매핑이 스키마와 일치하는지 실제로 검증한다.
 * (V1 이 엔티티와 어긋나면 validate 단계에서 컨텍스트 로드가 실패한다.)
 *
 * 실행에 Docker 가 필요하다(Testcontainers). @ServiceConnection 이 컨테이너의
 * datasource(url/user/password)를 자동 주입하므로 application.properties 의 로컬 DB 설정은 무시된다.
 */
@SpringBootTest
@Testcontainers
class CmsWebApplicationTests {

	@Container
	@ServiceConnection
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

	@Test
	void contextLoads() {
	}

}
