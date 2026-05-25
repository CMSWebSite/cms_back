package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.Journal;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    /** Dashboard 등 비페이징 호출 용. */
    List<Journal> findAllByOrderByPublishedDateDesc();

    /**
     * 키워드 + 노출 여부 필터링 가능한 페이징 검색.
     * visible == null 이면 노출 여부 무관(관리자 목록용),
     * visible == true 면 공개 사이트 목록.
     */
    @Query("""
        SELECT j FROM Journal j
        WHERE (:visible IS NULL OR j.visible = :visible)
          AND (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(j.title1) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(j.title2, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(j.authors) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(j.journalName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<Journal> search(@Param("visible") Boolean visible,
                         @Param("keyword") String keyword,
                         Pageable pageable);
}
