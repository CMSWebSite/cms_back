package cms_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cms_back.domain.SiteSetting;

import java.util.List;
import java.util.Optional;

public interface SiteSettingRepository extends JpaRepository<SiteSetting, Long> {
    Optional<SiteSetting> findByKey(String key);
    List<SiteSetting> findAllByOrderByKeyAsc();
    boolean existsByKey(String key);
}
