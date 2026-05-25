package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.Conference;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    @Query("""
        SELECT c FROM Conference c
        WHERE (:visible IS NULL OR c.visible = :visible)
          AND (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(c.authors) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(c.conferenceName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(c.location, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<Conference> search(@Param("visible") Boolean visible,
                            @Param("keyword") String keyword,
                            Pageable pageable);
}
