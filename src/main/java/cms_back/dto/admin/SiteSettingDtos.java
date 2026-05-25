package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import cms_back.domain.SiteSetting;

import java.time.LocalDateTime;

public final class SiteSettingDtos {

    private SiteSettingDtos() {}

    public record Item(
            Long id,
            String key,
            String value,
            String type,
            String description,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        public static Item from(SiteSetting s) {
            return new Item(s.getId(), s.getKey(), s.getValue(),
                    s.getType(), s.getDescription(),
                    s.getUpdatedAt(), s.getUpdatedBy());
        }
    }

    /** 단일 key의 값을 upsert 한다. */
    public record UpsertRequest(
            @NotBlank(message = "key는 필수입니다.") @Size(max = 100) String key,
            String value,
            @Size(max = 30) String type,
            @Size(max = 300) String description
    ) {}

    /** 한 번에 여러 키를 저장한다 (예: ContactUs 일괄 저장). */
    public record BulkUpsertRequest(
            java.util.List<UpsertRequest> settings
    ) {}
}
