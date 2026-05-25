package cms_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cms_back.domain.VisionMissionSection;

import java.util.List;
import java.util.Optional;

public interface VisionMissionSectionRepository extends JpaRepository<VisionMissionSection, Long> {
    List<VisionMissionSection> findAllByOrderBySortOrderAscIdAsc();
    List<VisionMissionSection> findByVisibleTrueOrderBySortOrderAscIdAsc();
    Optional<VisionMissionSection> findBySectionKey(String sectionKey);
    boolean existsBySectionKey(String sectionKey);
}
