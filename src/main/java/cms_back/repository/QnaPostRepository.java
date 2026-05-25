package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.QnaPost;

public interface QnaPostRepository extends JpaRepository<QnaPost, Long> {

    long countByAnsweredFalse();

    @Query("""
        SELECT q FROM QnaPost q
        WHERE (:visible IS NULL OR q.visible = :visible)
          AND (:answered IS NULL OR q.answered = :answered)
          AND (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(q.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(q.writerName) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<QnaPost> search(@Param("visible") Boolean visible,
                         @Param("answered") Boolean answered,
                         @Param("keyword") String keyword,
                         Pageable pageable);
}
