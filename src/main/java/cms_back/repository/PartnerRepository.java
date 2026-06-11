package cms_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cms_back.domain.Partner;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    List<Partner> findAllByOrderBySortOrderAscIdAsc();
    List<Partner> findByVisibleTrueOrderBySortOrderAscIdAsc();
}
