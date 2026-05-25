package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.Project;
import cms_back.domain.ProjectStatus;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        SELECT p FROM Project p
        WHERE (:visible IS NULL OR p.visible = :visible)
          AND (:status IS NULL OR p.status = :status)
          AND (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(p.description, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(p.fundingAgency, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<Project> search(@Param("visible") Boolean visible,
                         @Param("status") ProjectStatus status,
                         @Param("keyword") String keyword,
                         Pageable pageable);
}
