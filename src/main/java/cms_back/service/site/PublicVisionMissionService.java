package cms_back.service.site;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.dto.site.PublicVisionMissionDtos;
import cms_back.repository.VisionMissionSectionRepository;

import java.util.List;

@Service
public class PublicVisionMissionService {

    private final VisionMissionSectionRepository repository;

    public PublicVisionMissionService(VisionMissionSectionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PublicVisionMissionDtos.Section> list() {
        return repository.findByVisibleTrueOrderBySortOrderAscIdAsc().stream()
                .map(PublicVisionMissionDtos.Section::from)
                .toList();
    }
}
