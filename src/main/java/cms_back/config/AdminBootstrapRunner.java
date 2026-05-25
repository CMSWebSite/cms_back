package cms_back.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.UserRole;
import cms_back.repository.UserRepository;

/**
 * 애플리케이션 부팅 시 지정한 이메일의 사용자를 ADMIN으로 승격시킨다.
 * application.properties에 app.admin.bootstrap-email 설정.
 * 일반 회원가입으로 계정을 만든 뒤 이 부트스트랩으로 1회만 권한을 올리면 된다.
 */
@Component
public class AdminBootstrapRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrapRunner.class);

    private final UserRepository userRepository;
    private final String bootstrapEmail;

    public AdminBootstrapRunner(UserRepository userRepository,
                                @Value("${app.admin.bootstrap-email:}") String bootstrapEmail) {
        this.userRepository = userRepository;
        this.bootstrapEmail = bootstrapEmail == null ? "" : bootstrapEmail.trim().toLowerCase();
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (bootstrapEmail.isEmpty()) {
            return;
        }
        userRepository.findByEmail(bootstrapEmail).ifPresentOrElse(user -> {
            if (user.getRole() != UserRole.ADMIN) {
                user.changeRole(UserRole.ADMIN);
                log.info("[AdminBootstrap] 사용자 {} 를 ADMIN으로 승격했습니다.", bootstrapEmail);
            }
        }, () -> log.info(
                "[AdminBootstrap] 부트스트랩 대상 이메일 {} 에 해당하는 사용자가 아직 없습니다. "
                        + "먼저 /signup 으로 가입 후 재기동하면 ADMIN으로 승격됩니다.",
                bootstrapEmail));
    }
}
