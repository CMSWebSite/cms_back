package cms_back.service.site;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.dto.site.PublicFacilityDtos;
import cms_back.repository.FacilityRepository;

import java.util.List;

@Service
public class PublicFacilityService {

    private final FacilityRepository repository;

    public PublicFacilityService(FacilityRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PublicFacilityDtos.Item> list() {
        return repository.findByVisibleTrueOrderBySortOrderAscNameAsc().stream()
                .map(PublicFacilityDtos.Item::from)
                .toList();
    }
}
