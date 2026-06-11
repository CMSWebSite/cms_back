package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Facility;
import cms_back.dto.admin.FacilityDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.FacilityRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class AdminFacilityService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.ASC, "sortOrder").and(Sort.by("name"));

    private final FacilityRepository repository;
    private final AdminContext adminContext;

    public AdminFacilityService(FacilityRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public PageResponse<FacilityDtos.Summary> list(int page, int limit, String keyword,
                                                   String category, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(null, category, keyword, pageable),
                FacilityDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public FacilityDtos.Detail get(Long id) {
        return FacilityDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public FacilityDtos.Detail create(FacilityDtos.UpsertRequest req) {
        Facility saved = repository.save(Facility.create(
                req.name(), req.category(), req.description(), req.specification(),
                req.image(), req.quantity(), req.sortOrder(), req.visible(),
                adminContext.currentActor()
        ));
        return FacilityDtos.Detail.from(saved);
    }

    @Transactional
    public FacilityDtos.Detail update(Long id, FacilityDtos.UpsertRequest req) {
        Facility f = findOrThrow(id);
        f.update(req.name(), req.category(), req.description(), req.specification(),
                req.image(), req.quantity(), req.sortOrder(), req.visible(),
                adminContext.currentActor());
        return FacilityDtos.Detail.from(f);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Facility findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 장비를 찾을 수 없습니다."));
    }
}
