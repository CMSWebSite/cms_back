package cms_back.dto.admin;

import jakarta.validation.constraints.NotNull;

import cms_back.domain.User;
import cms_back.domain.UserRole;

import java.time.LocalDateTime;

public final class UserDtos {

    private UserDtos() {}

    public record Summary(
            Long id,
            String name,
            String email,
            UserRole role,
            LocalDateTime createdAt
    ) {
        public static Summary from(User u) {
            return new Summary(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.getCreatedAt());
        }
    }

    public record ChangeRoleRequest(
            @NotNull(message = "변경할 권한을 지정해야 합니다.")
            UserRole role
    ) {}
}
