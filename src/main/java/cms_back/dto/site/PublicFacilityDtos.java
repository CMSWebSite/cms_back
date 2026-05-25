package cms_back.dto.site;

import cms_back.domain.Facility;

public final class PublicFacilityDtos {

    private PublicFacilityDtos() {}

    public record Item(
            Long id,
            String name,
            String category,
            String description,
            String specification,
            String image,
            int quantity
    ) {
        public static Item from(Facility f) {
            return new Item(f.getId(), f.getName(), f.getCategory(),
                    f.getDescription(), f.getSpecification(),
                    f.getImage(), f.getQuantity());
        }
    }
}
