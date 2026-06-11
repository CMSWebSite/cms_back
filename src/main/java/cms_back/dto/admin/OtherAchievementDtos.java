package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import cms_back.domain.OtherAchievement;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class OtherAchievementDtos {

    private OtherAchievementDtos() {}

    public record Summary(
            Long id,
            String title,
            String type,
            LocalDate achievedOn,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Summary from(OtherAchievement o) {
            return new Summary(
                    o.getId(), o.getTitle(), o.getType(),
                    o.getAchievedOn(), o.isVisible(), o.getUpdatedAt()
            );
        }
    }

    public record Detail(
            Long id,
            String title,
            String type,
            String description,
            LocalDate achievedOn,
            String attachmentName,
            String attachmentUrl,
            Long attachmentSize,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy
    ) {
        public static Detail from(OtherAchievement o) {
            return new Detail(
                    o.getId(), o.getTitle(), o.getType(), o.getDescription(),
                    o.getAchievedOn(),
                    o.getAttachmentName(), o.getAttachmentUrl(), o.getAttachmentSize(),
                    o.isVisible(),
                    o.getCreatedAt(), o.getUpdatedAt(), o.getCreatedBy(), o.getUpdatedBy()
            );
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "제목은 필수입니다.")
            @Size(max = 500)
            String title,

            @NotBlank(message = "분류는 필수입니다.")
            @Size(max = 50)
            String type,

            String description,

            @NotNull(message = "날짜는 필수입니다.")
            LocalDate achievedOn,

            @Size(max = 300) String attachmentName,
            @Size(max = 500) String attachmentUrl,
            Long attachmentSize,

            boolean visible
    ) {}
}
