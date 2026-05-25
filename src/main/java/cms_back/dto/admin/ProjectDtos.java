package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import cms_back.domain.Project;
import cms_back.domain.ProjectStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ProjectDtos {

    private ProjectDtos() {}

    public record Summary(
            Long id,
            String title,
            LocalDate startDate,
            LocalDate endDate,
            String fundingAgency,
            String role,
            ProjectStatus status,
            String thumbnailImage,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Summary from(Project p) {
            return new Summary(
                    p.getId(), p.getTitle(),
                    p.getStartDate(), p.getEndDate(),
                    p.getFundingAgency(), p.getRole(), p.getStatus(),
                    p.getThumbnailImage(), p.isVisible(), p.getUpdatedAt()
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
            String detailContent,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy
    ) {
        public static Detail from(Project p) {
            return new Detail(
                    p.getId(), p.getTitle(), p.getDescription(),
                    p.getStartDate(), p.getEndDate(),
                    p.getFundingAgency(), p.getRole(), p.getStatus(),
                    p.getThumbnailImage(), p.getDetailContent(),
                    p.isVisible(),
                    p.getCreatedAt(), p.getUpdatedAt(), p.getCreatedBy(), p.getUpdatedBy()
            );
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "과제명은 필수입니다.")
            @Size(max = 300)
            String title,

            String description,

            @NotNull(message = "시작일은 필수입니다.")
            LocalDate startDate,

            LocalDate endDate,

            @Size(max = 200) String fundingAgency,
            @Size(max = 100) String role,

            @NotNull(message = "상태는 필수입니다.")
            ProjectStatus status,

            @Size(max = 500) String thumbnailImage,
            String detailContent,

            boolean visible
    ) {}
}
