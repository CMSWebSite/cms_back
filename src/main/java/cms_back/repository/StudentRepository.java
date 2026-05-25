package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.Student;
import cms_back.domain.StudentStatus;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByOrderByEnrolledAtDesc();
    Optional<Student> findBySlug(String slug);
    Optional<Student> findBySlugAndVisibleTrue(String slug);
    boolean existsBySlug(String slug);
    long countByStatus(StudentStatus status);

    /**
     * 키워드(한글/영문 이름·role), 상태, 노출 여부 필터링 가능한 페이징 검색.
     * visible == null 이면 노출 여부 무관(관리자 목록).
     */
    @Query("""
        SELECT s FROM Student s
        WHERE (:visible IS NULL OR s.visible = :visible)
          AND (:status IS NULL OR s.status = :status)
          AND (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(s.koreanName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(s.englishName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(s.role) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<Student> search(@Param("visible") Boolean visible,
                         @Param("status") StudentStatus status,
                         @Param("keyword") String keyword,
                         Pageable pageable);
}
