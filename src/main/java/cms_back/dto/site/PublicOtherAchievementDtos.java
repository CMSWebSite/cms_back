package cms_back.dto.site;

import cms_back.domain.OtherAchievement;

import java.time.LocalDate;

public final class PublicOtherAchievementDtos {

    private PublicOtherAchievementDtos() {}

    public record Summary(
            Long id,
            String title,
            String type,
            LocalDate achievedOn,
            String attachmentUrl
    ) {
        public static Summary from(OtherAchievement o) {
            return new Summary(
                    o.getId(), o.getTitle(), o.getType(),
                    o.getAchievedOn(), o.getAttachmentUrl()
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
            Long attachmentSize
    ) {
        public static Detail from(OtherAchievement o) {
            return new Detail(
                    o.getId(), o.getTitle(), o.getType(), o.getDescription(),
                    o.getAchievedOn(),
                    o.getAttachmentName(), o.getAttachmentUrl(), o.getAttachmentSize()
            );
        }
    }
}
