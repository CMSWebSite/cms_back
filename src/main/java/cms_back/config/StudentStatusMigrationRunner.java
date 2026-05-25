package cms_back.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Phase C에서 StudentStatus enum의 명칭을 정리하면서,
 * 기존 DB의 'GRADUATED' / 'LEAVE' 값을 'ALUMNI' / 'INACTIVE' 로 변환한다.
 * 멱등하므로 여러 번 실행되어도 안전하다.
 */
@Component
@Order(0)
public class StudentStatusMigrationRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StudentStatusMigrationRunner.class);

    private final JdbcTemplate jdbc;

    public StudentStatusMigrationRunner(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) {
        try {
            int g = jdbc.update("UPDATE students SET status = 'ALUMNI'   WHERE status = 'GRADUATED'");
            int l = jdbc.update("UPDATE students SET status = 'INACTIVE' WHERE status = 'LEAVE'");
            if (g > 0 || l > 0) {
                log.info("[StudentStatusMigration] GRADUATED→ALUMNI: {} rows, LEAVE→INACTIVE: {} rows", g, l);
            }
        } catch (Exception e) {
            // 테이블이 아직 없거나 권한 문제 등으로 실패해도 부팅을 막지 않는다.
            log.warn("[StudentStatusMigration] skipped: {}", e.getMessage());
        }
    }
}
