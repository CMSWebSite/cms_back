package cms_back.dto.site;

import cms_back.domain.VisionMissionSection;

import java.util.List;

public final class PublicVisionMissionDtos {

    private PublicVisionMissionDtos() {}

    public record Section(
            Long id,
            String sectionKey,
            String title,
            String subtitle,
            String content,
            List<String> items,
            String image,
            int sortOrder
    ) {
        public static Section from(VisionMissionSection v) {
            return new Section(v.getId(), v.getSectionKey(), v.getTitle(), v.getSubtitle(),
                    v.getContent(), v.getItems(), v.getImage(), v.getSortOrder());
        }
    }
}
