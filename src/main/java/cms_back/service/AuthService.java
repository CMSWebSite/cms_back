package cms_back.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.User;
import cms_back.dto.SignupRequest;
import cms_back.dto.SignupResponse;
import cms_back.repository.UserRepository;
import cms_back.service.exception.EmailAlreadyExistsException;
import cms_back.service.exception.PasswordMismatchException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignupResponse signup(SignupRequest req) {
        if (!req.getPassword().equals(req.getPasswordConfirm())) {
            throw new PasswordMismatchException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        String email = req.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("이미 가입된 이메일입니다.");
        }

        String passwordHash = passwordEncoder.encode(req.getPassword());

        // 요구사항: 회원가입 시에는 무조건 일반회원
        User user = User.createNormal(req.getName().trim(), email, passwordHash);
        User saved = userRepository.save(user);

        return new SignupResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole(),
                saved.getCreatedAt()
        );
    }
}
