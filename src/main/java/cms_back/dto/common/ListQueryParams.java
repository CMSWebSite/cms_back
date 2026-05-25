package cms_back.dto.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 목록 API의 page/limit/sort 쿼리 파라미터를 Spring Data {@link Pageable}로 변환한다.
 * - page는 1-인덱스, Spring Data는 0-인덱스이므로 내부에서 1을 뺀다.
 * - sort 포맷: "field_asc" 또는 "field_desc". 예: "publishedDate_desc".
 * - limit는 1~100 사이로 강제 한정한다.
 */
public final class ListQueryParams {

    private static final int MAX_LIMIT = 100;
    private static final int MIN_LIMIT = 1;

    private ListQueryParams() {}

    public static Pageable toPageable(int page, int limit, String sort, Sort defaultSort) {
        int safePage = Math.max(0, page - 1);
        int safeLimit = Math.min(MAX_LIMIT, Math.max(MIN_LIMIT, limit));
        return PageRequest.of(safePage, safeLimit, parseSort(sort, defaultSort));
    }

    private static Sort parseSort(String sort, Sort defaultSort) {
        if (sort == null || sort.isBlank()) {
            return defaultSort;
        }
        int idx = sort.lastIndexOf('_');
        if (idx <= 0 || idx == sort.length() - 1) {
            // "field" 형태만 들어오면 기본 ASC.
            return Sort.by(sort);
        }
        String field = sort.substring(0, idx);
        String dir = sort.substring(idx + 1).toLowerCase();
        return "desc".equals(dir) ? Sort.by(field).descending() : Sort.by(field).ascending();
    }
}
