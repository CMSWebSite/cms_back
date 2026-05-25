package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import cms_back.domain.Student;
import cms_back.domain.StudentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class StudentDtos {

    private StudentDtos() {}

    public record Summary(
            Long id,
            String slug,
            String koreanName,
            String englishName,
            LocalDate enrolledAt,
            String role,
            StudentStatus status,
            String photoUrl,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Summary from(Student s) {
            return new Summary(
                    s.getId(), s.getSlug(), s.getKoreanName(), s.getEnglishName(),
                    s.getEnrolledAt(), s.getRole(), s.getStatus(), s.getPhotoUrl(),
                    s.isVisible(), s.getUpdatedAt()
            );
        }
    }

    public record Detail(
            Long id,
            String slug,
            String koreanName,
            String englishName,
            LocalDate enrolledAt,
            String role,
            List<String> majors,
            List<String> researchInterests,
            List<String> career,
            List<String> publications,
            String email,
            String photoUrl,
            StudentStatus status,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy
    ) {
        public static Detail from(Student s) {
            return new Detail(
                    s.getId(), s.getSlug(), s.getKoreanName(), s.getEnglishName(),
                    s.getEnrolledAt(), s.getRole(),
                    s.getMajors(), s.getResearchInterests(), s.getCareer(), s.getPublications(),
                    s.getEmail(), s.getPhotoUrl(),
                    s.getStatus(), s.isVisible(),
                    s.getCreatedAt(), s.getUpdatedAt(), s.getCreatedBy(), s.getUpdatedBy()
            );
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "슬러그(URL 식별자)는 필수입니다.")
            @Size(max = 100)
            @Pattern(regexp = "^[a-z0-9][a-z0-9-]*$",
                    message = "슬러그는 소문자, 숫자, 하이픈만 사용 가능합니다.")
            String slug,

            @NotBlank(message = "한글이름은 필수입니다.")
            @Size(max = 50)
            String koreanName,

            @NotBlank(message = "영문이름은 필수입니다.")
            @Size(max = 100)
            String englishName,

            @NotNull(message = "입학연월은 필수입니다.")
            LocalDate enrolledAt,

            @NotBlank(message = "역할/직책은 필수입니다.")
            @Size(max = 100)
            String role,

            List<String> majors,
            List<String> researchInterests,
            List<String> career,
            List<String> publications,

            @Size(max = 200) String email,

            @Size(max = 500) String photoUrl,

            @NotNull(message = "상태는 필수입니다.")
            StudentStatus status,

            boolean visible
    ) {}
}
