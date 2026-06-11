package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.Facility;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    /** 공개 사이트용 — 노출 항목만 sortOrder ASC, name ASC. */
    List<Facility> findByVisibleTrueOrderBySortOrderAscNameAsc();

    @Query("""
        SELECT f FROM Facility f
        WHERE (:visible IS NULL OR f.visible = :visible)
          AND (:category IS NULL OR :category = '' OR f.category = :category)
          AND (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(f.description, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<Facility> search(@Param("visible") Boolean visible,
                          @Param("category") String category,
                          @Param("keyword") String keyword,
                          Pageable pageable);
}
