package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.Patent;
import cms_back.domain.PatentStatus;

public interface PatentRepository extends JpaRepository<Patent, Long> {

    @Query("""
        SELECT p FROM Patent p
        WHERE (:visible IS NULL OR p.visible = :visible)
          AND (:status IS NULL OR p.status = :status)
          AND (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.inventors) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(p.applicationNumber, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(p.registrationNumber, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<Patent> search(@Param("visible") Boolean visible,
                        @Param("status") PatentStatus status,
                        @Param("keyword") String keyword,
                        Pageable pageable);
}
