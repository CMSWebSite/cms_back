package cms_back.dto.site;

import cms_back.domain.News;

import java.time.LocalDateTime;

/**
 * 일반 사용자에게 노출되는 News DTO. 관리자 메타(작성자/수정자 등)는 제외한다.
 */
public final class PublicNewsDtos {

    private PublicNewsDtos() {}

    public record Summary(
            Long id,
            String title,
            LocalDateTime publishedAt,
            String coverImageUrl
    ) {
        public static Summary from(News n) {
            return new Summary(n.getId(), n.getTitle(), n.getPublishedAt(), n.getCoverImageUrl());
        }
    }

    public record Detail(
            Long id,
            String title,
            String content,
            String coverImageUrl,
            LocalDateTime publishedAt
    ) {
        public static Detail from(News n) {
            return new Detail(
                    n.getId(), n.getTitle(), n.getContent(),
                    n.getCoverImageUrl(), n.getPublishedAt()
            );
        }
    }
}
