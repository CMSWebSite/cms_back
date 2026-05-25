package cms_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cms_back.domain.Professor;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findFirstByOrderByIdAsc();
    Optional<Professor> findFirstByVisibleTrueOrderByIdAsc();
}
