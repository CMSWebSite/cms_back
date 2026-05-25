package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Patent;
import cms_back.domain.PatentStatus;
import cms_back.dto.admin.PatentDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.PatentRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class AdminPatentService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "applicationDate");

    private final PatentRepository repository;
    private final AdminContext adminContext;

    public AdminPatentService(PatentRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public PageResponse<PatentDtos.Summary> list(int page, int limit, String keyword,
                                                 PatentStatus status, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(null, status, keyword, pageable),
                PatentDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public PatentDtos.Detail get(Long id) {
        return PatentDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public PatentDtos.Detail create(PatentDtos.UpsertRequest req) {
        Patent saved = repository.save(Patent.create(
                req.title(), req.inventors(),
                req.applicationNumber(), req.registrationNumber(),
                req.applicationDate(), req.registrationDate(),
                req.status(), req.abstractText(),
                req.visible(),
                adminContext.currentActor()
        ));
        return PatentDtos.Detail.from(saved);
    }

    @Transactional
    public PatentDtos.Detail update(Long id, PatentDtos.UpsertRequest req) {
        Patent p = findOrThrow(id);
        p.update(req.title(), req.inventors(),
                req.applicationNumber(), req.registrationNumber(),
                req.applicationDate(), req.registrationDate(),
                req.status(), req.abstractText(),
                req.visible(),
                adminContext.currentActor());
        return PatentDtos.Detail.from(p);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Patent findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 특허를 찾을 수 없습니다."));
    }
}
