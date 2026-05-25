package cms_back.dto.common;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/**
 * 모든 목록 API의 페이지네이션 응답 포맷.
 * {@code page}는 1부터 시작한다 (Spring Data의 0-인덱스를 1-인덱스로 변환).
 */
public record PageResponse<T>(
        List<T> items,
        int page,
        int limit,
        long totalItems,
        int totalPages
) {

    public static <S, T> PageResponse<T> from(Page<S> source, Function<S, T> mapper) {
        return new PageResponse<>(
                source.getContent().stream().map(mapper).toList(),
                source.getNumber() + 1,
                source.getSize(),
                source.getTotalElements(),
                source.getTotalPages()
        );
    }
}
