package cms_back.dto.site;

import cms_back.domain.Professor;

import java.util.List;

public final class PublicProfessorDtos {

    private PublicProfessorDtos() {}

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
            List<String> publications
    ) {
        public static Detail from(Professor p) {
            return new Detail(
                    p.getId(), p.getName(), p.getPosition(), p.getProfileImage(),
                    p.getEmail(), p.getPhone(), p.getOffice(), p.getBiography(),
                    p.getResearchFields(), p.getEducation(), p.getCareer(), p.getPublications()
            );
        }
    }
}
