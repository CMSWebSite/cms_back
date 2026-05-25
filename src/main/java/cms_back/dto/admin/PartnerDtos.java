package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import cms_back.domain.Partner;

import java.time.LocalDateTime;

public final class PartnerDtos {

    private PartnerDtos() {}

    public record Item(
            Long id,
            String name,
            String logoUrl,
            String websiteUrl,
            int sortOrder,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Item from(Partner p) {
            return new Item(p.getId(), p.getName(), p.getLogoUrl(),
                    p.getWebsiteUrl(), p.getSortOrder(), p.isVisible(),
                    p.getUpdatedAt());
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "이름은 필수입니다.") @Size(max = 200) String name,
            @Size(max = 500) String logoUrl,
            @Size(max = 500) String websiteUrl,
            @PositiveOrZero int sortOrder,
            boolean visible
    ) {}
}
