package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import cms_back.domain.Professor;

import java.time.LocalDateTime;
import java.util.List;

public final class ProfessorDtos {

    private ProfessorDtos() {}

    public record Detail(
            Long id,
            String name,
            String position,
            String profileImage,
            String email,
            String phone,
            String office,
            String biography,
            List<String> researchFields,
            List<String> education,
            List<String> career,
            List<String> publications,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Detail from(Professor p) {
            return new Detail(
                    p.getId(), p.getName(), p.getPosition(), p.getProfileImage(),
                    p.getEmail(), p.getPhone(), p.getOffice(), p.getBiography(),
                    p.getResearchFields(), p.getEducation(), p.getCareer(), p.getPublications(),
                    p.isVisible(),
                    p.getCreatedAt(), p.getUpdatedAt()
            );
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "이름은 필수입니다.")
            @Size(max = 100)
            String name,

            @Size(max = 200) String position,
            @Size(max = 500) String profileImage,
            @Size(max = 200) String email,
            @Size(max = 50) String phone,
            @Size(max = 200) String office,
            String biography,

            List<String> researchFields,
            List<String> education,
            List<String> career,
            List<String> publications,

            boolean visible
    ) {}
}
