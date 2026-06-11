package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import cms_back.domain.VisionMissionSection;

import java.time.LocalDateTime;
import java.util.List;

public final class VisionMissionDtos {

    private VisionMissionDtos() {}

    public record Section(
            Long id,
            String sectionKey,
            String title,
            String subtitle,
            String content,
            List<String> items,
            String image,
            int sortOrder,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Section from(VisionMissionSection v) {
            return new Section(v.getId(), v.getSectionKey(), v.getTitle(), v.getSubtitle(),
                    v.getContent(), v.getItems(), v.getImage(),
                    v.getSortOrder(), v.isVisible(), v.getUpdatedAt());
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "섹션 키는 필수입니다.") @Size(max = 100) String sectionKey,
            @Size(max = 300) String title,
            @Size(max = 300) String subtitle,
            String content,
            List<String> items,
            @Size(max = 500) String image,
            @PositiveOrZero int sortOrder,
            boolean visible
    ) {}
}
