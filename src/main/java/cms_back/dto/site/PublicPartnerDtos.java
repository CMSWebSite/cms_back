package cms_back.dto.site;

import cms_back.domain.Partner;

public final class PublicPartnerDtos {

    private PublicPartnerDtos() {}

    public record Item(
            Long id,
            String name,
            String logoUrl,
            String websiteUrl
    ) {
        public static Item from(Partner p) {
            return new Item(p.getId(), p.getName(), p.getLogoUrl(), p.getWebsiteUrl());
        }
    }
}
