package cms_back.dto.site;

import cms_back.domain.Student;
import cms_back.domain.StudentStatus;

import java.time.LocalDate;
import java.util.List;

public final class PublicStudentDtos {

    private PublicStudentDtos() {}

    public record Summary(
            Long id,
            String slug,
            String koreanName,
            String englishName,
            LocalDate enrolledAt,
            String role,
            StudentStatus status,
            String photoUrl,
            List<String> majors
    ) {
        public static Summary from(Student s) {
            return new Summary(
                    s.getId(), s.getSlug(), s.getKoreanName(), s.getEnglishName(),
                    s.getEnrolledAt(), s.getRole(), s.getStatus(),
                    s.getPhotoUrl(), s.getMajors()
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
            StudentStatus status
    ) {
        public static Detail from(Student s) {
            return new Detail(
                    s.getId(), s.getSlug(), s.getKoreanName(), s.getEnglishName(),
                    s.getEnrolledAt(), s.getRole(),
                    s.getMajors(), s.getResearchInterests(), s.getCareer(), s.getPublications(),
                    s.getEmail(), s.getPhotoUrl(),
                    s.getStatus()
            );
        }
    }
}
