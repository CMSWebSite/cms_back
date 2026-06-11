package cms_back.dto.site;

import cms_back.domain.Project;
import cms_back.domain.ProjectStatus;

import java.time.LocalDate;

public final class PublicProjectDtos {

    private PublicProjectDtos() {}

    public record Summary(
            Long id,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            String fundingAgency,
            ProjectStatus status,
            String thumbnailImage
    ) {
        public static Summary from(Project p) {
            return new Summary(
                    p.getId(), p.getTitle(), p.getDescription(),
                    p.getStartDate(), p.getEndDate(),
                    p.getFundingAgency(), p.getStatus(),
                    p.getThumbnailImage()
            );
        }
    }

    public record Detail(
            Long id,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            String fundingAgency,
            String role,
            ProjectStatus status,
            String thumbnailImage,
            String detailContent
    ) {
        public static Detail from(Project p) {
            return new Detail(
                    p.getId(), p.getTitle(), p.getDescription(),
                    p.getStartDate(), p.getEndDate(),
                    p.getFundingAgency(), p.getRole(), p.getStatus(),
                    p.getThumbnailImage(), p.getDetailContent()
            );
        }
    }
}
