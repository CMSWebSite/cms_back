package cms_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cms_back.domain.TestEntity;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}

