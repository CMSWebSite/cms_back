package cms_back.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Partner;
import cms_back.dto.admin.PartnerDtos;
import cms_back.repository.PartnerRepository;
import cms_back.service.exception.NotFoundException;

import java.util.List;

@Service
public class AdminPartnerService {

    private final PartnerRepository repository;
    private final AdminContext adminContext;

    public AdminPartnerService(PartnerRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public List<PartnerDtos.Item> list() {
        return repository.findAllByOrderBySortOrderAscIdAsc().stream()
                .map(PartnerDtos.Item::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PartnerDtos.Item get(Long id) {
        return PartnerDtos.Item.from(findOrThrow(id));
    }

    @Transactional
    public PartnerDtos.Item create(PartnerDtos.UpsertRequest req) {
        Partner saved = repository.save(Partner.create(
                req.name(), req.logoUrl(), req.websiteUrl(),
                req.sortOrder(), req.visible(), adminContext.currentActor()
        ));
        return PartnerDtos.Item.from(saved);
    }

    @Transactional
    public PartnerDtos.Item update(Long id, PartnerDtos.UpsertRequest req) {
        Partner p = findOrThrow(id);
        p.update(req.name(), req.logoUrl(), req.websiteUrl(),
                req.sortOrder(), req.visible(), adminContext.currentActor());
        return PartnerDtos.Item.from(p);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Partner findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 파트너를 찾을 수 없습니다."));
    }
}
