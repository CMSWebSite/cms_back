package cms_back.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.VisionMissionSection;
import cms_back.dto.admin.VisionMissionDtos;
import cms_back.repository.VisionMissionSectionRepository;
import cms_back.service.exception.BusinessRuleException;
import cms_back.service.exception.NotFoundException;

import java.util.List;

@Service
public class AdminVisionMissionService {

    private final VisionMissionSectionRepository repository;
    private final AdminContext adminContext;

    public AdminVisionMissionService(VisionMissionSectionRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public List<VisionMissionDtos.Section> list() {
        return repository.findAllByOrderBySortOrderAscIdAsc().stream()
                .map(VisionMissionDtos.Section::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public VisionMissionDtos.Section get(Long id) {
        return VisionMissionDtos.Section.from(findOrThrow(id));
    }

    @Transactional
    public VisionMissionDtos.Section create(VisionMissionDtos.UpsertRequest req) {
        if (repository.existsBySectionKey(req.sectionKey())) {
            throw new BusinessRuleException("SECTION_KEY_DUPLICATE", "이미 사용 중인 섹션 키입니다.");
        }
        VisionMissionSection saved = repository.save(VisionMissionSection.create(
                req.sectionKey(), req.title(), req.subtitle(),
                req.content(), req.items(), req.image(),
                req.sortOrder(), req.visible(),
                adminContext.currentActor()
        ));
        return VisionMissionDtos.Section.from(saved);
    }

    @Transactional
    public VisionMissionDtos.Section update(Long id, VisionMissionDtos.UpsertRequest req) {
        VisionMissionSection v = findOrThrow(id);
        if (!v.getSectionKey().equals(req.sectionKey()) && repository.existsBySectionKey(req.sectionKey())) {
            throw new BusinessRuleException("SECTION_KEY_DUPLICATE", "이미 사용 중인 섹션 키입니다.");
        }
        v.update(req.sectionKey(), req.title(), req.subtitle(),
                req.content(), req.items(), req.image(),
                req.sortOrder(), req.visible(),
                adminContext.currentActor());
        return VisionMissionDtos.Section.from(v);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private VisionMissionSection findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 섹션을 찾을 수 없습니다."));
    }
}
