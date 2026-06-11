package cms_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cms_back.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}