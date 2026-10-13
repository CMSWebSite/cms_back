package cms_back.dto.admin;

import cms_back.domain.ContactApplication;

import java.time.LocalDateTime;

public final class ContactApplicationDtos {

    private ContactApplicationDtos() {}

    public record Summary(
            Long id,
            String name,
            String contact,
            String major,
            LocalDateTime createdAt
    ) {
        public static Summary from(ContactApplication c) {
            return new Summary(c.getId(), c.getName(), c.getContact(),
                    c.getMajor(), c.getCreatedAt());
        }
    }

    public record Detail(
            Long id,
            String name,
            String contact,
            String major,
            String introduce,
            LocalDateTime createdAt
    ) {
        public static Detail from(ContactApplication c) {
            return new Detail(c.getId(), c.getName(), c.getContact(),
                    c.getMajor(), c.getIntroduce(), c.getCreatedAt());
        }
    }
}
