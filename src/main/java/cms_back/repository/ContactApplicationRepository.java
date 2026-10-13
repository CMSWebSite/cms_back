package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.ContactApplication;

public interface ContactApplicationRepository extends JpaRepository<ContactApplication, Long> {

    @Query("""
        SELECT c FROM ContactApplication c
        WHERE (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(c.contact) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(c.major) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(c.introduce) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        """)
    Page<ContactApplication> search(@Param("keyword") String keyword, Pageable pageable);
}
