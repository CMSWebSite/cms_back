package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.OtherAchievement;

public interface OtherAchievementRepository extends JpaRepository<OtherAchievement, Long> {

    @Query("""
        SELECT o FROM OtherAchievement o
        WHERE (:visible IS NULL OR o.visible = :visible)
          AND (:type IS NULL OR :type = '' OR o.type = :type)
          AND (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(o.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(o.description, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<OtherAchievement> search(@Param("visible") Boolean visible,
                                  @Param("type") String type,
                                  @Param("keyword") String keyword,
                                  Pageable pageable);
}
