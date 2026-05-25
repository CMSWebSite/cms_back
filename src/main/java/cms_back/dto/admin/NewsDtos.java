package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import cms_back.domain.News;

import java.time.LocalDateTime;

/**
 * 관리자 News 도메인 DTO 모음. 외부에서는 {@code NewsDtos.Summary} 형태로 참조한다.
 */
public final class NewsDtos {

    private NewsDtos() {}

    /** 목록용 요약 정보. */
    public record Summary(
            Long id,
            String title,
            LocalDateTime publishedAt,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Summary from(News n) {
            return new Summary(n.getId(), n.getTitle(), n.getPublishedAt(), n.isVisible(), n.getUpdatedAt());
        }
    }

    /** 상세 조회/저장 응답. */
    public record Detail(
            Long id,
            String title,
            String content,
            String coverImageUrl,
            LocalDateTime publishedAt,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy
    ) {
        public static Detail from(News n) {
            return new Detail(
                    n.getId(), n.getTitle(), n.getContent(), n.getCoverImageUrl(),
                    n.getPublishedAt(), n.isVisible(),
                    n.getCreatedAt(), n.getUpdatedAt(), n.getCreatedBy(), n.getUpdatedBy()
            );
        }
    }

    /** 등록/수정 공통 요청. */
    public record UpsertRequest(
            @NotBlank(message = "제목은 필수입니다.")
            @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
            String title,

            @NotBlank(message = "본문은 필수입니다.")
            String content,

            @Size(max = 500)
            String coverImageUrl,

            @NotNull(message = "게시일은 필수입니다.")
            LocalDateTime publishedAt,

            boolean visible
    ) {}
}
