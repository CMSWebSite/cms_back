package cms_back.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.User;
import cms_back.domain.UserRole;
import cms_back.dto.admin.UserDtos;
import cms_back.repository.UserRepository;
import cms_back.service.exception.BusinessRuleException;
import cms_back.service.exception.NotFoundException;

import java.util.List;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final AdminContext adminContext;

    public AdminUserService(UserRepository userRepository, AdminContext adminContext) {
        this.userRepository = userRepository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public List<UserDtos.Summary> list() {
        return userRepository.findAll().stream()
                .map(UserDtos.Summary::from)
                .toList();
    }

    @Transactional
    public UserDtos.Summary changeRole(Long id, UserRole nextRole) {
        User target = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));

        // 본인 권한 강등 방지
        String myEmail = adminContext.currentActor();
        if (target.getEmail().equalsIgnoreCase(myEmail)
                && target.getRole() == UserRole.ADMIN
                && nextRole != UserRole.ADMIN) {
            throw new BusinessRuleException("SELF_DEMOTE_FORBIDDEN",
                    "본인 계정의 ADMIN 권한은 해제할 수 없습니다.");
        }

        // 마지막 ADMIN 강등 방지
        if (target.getRole() == UserRole.ADMIN && nextRole != UserRole.ADMIN) {
            long adminCount = userRepository.findAll().stream()
                    .filter(u -> u.getRole() == UserRole.ADMIN)
                    .count();
            if (adminCount <= 1) {
                throw new BusinessRuleException("LAST_ADMIN_FORBIDDEN",
                        "마지막 ADMIN의 권한은 강등할 수 없습니다.");
            }
        }

        target.changeRole(nextRole);
        return UserDtos.Summary.from(target);
    }
}
