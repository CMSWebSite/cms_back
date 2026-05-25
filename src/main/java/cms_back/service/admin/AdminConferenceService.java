package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Conference;
import cms_back.dto.admin.ConferenceDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.ConferenceRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class AdminConferenceService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "presentedDate");

    private final ConferenceRepository repository;
    private final AdminContext adminContext;

    public AdminConferenceService(ConferenceRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public PageResponse<ConferenceDtos.Summary> list(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(null, keyword, pageable),
                ConferenceDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public ConferenceDtos.Detail get(Long id) {
        return ConferenceDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public ConferenceDtos.Detail create(ConferenceDtos.UpsertRequest req) {
        Conference saved = repository.save(Conference.create(
                req.title(), req.authors(), req.conferenceName(),
                req.location(), req.presentedDate(), req.abstractText(),
                req.attachmentName(), req.attachmentUrl(), req.attachmentSize(),
                req.visible(),
                adminContext.currentActor()
        ));
        return ConferenceDtos.Detail.from(saved);
    }

    @Transactional
    public ConferenceDtos.Detail update(Long id, ConferenceDtos.UpsertRequest req) {
        Conference c = findOrThrow(id);
        c.update(req.title(), req.authors(), req.conferenceName(),
                req.location(), req.presentedDate(), req.abstractText(),
                req.attachmentName(), req.attachmentUrl(), req.attachmentSize(),
                req.visible(),
                adminContext.currentActor());
        return ConferenceDtos.Detail.from(c);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Conference findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 학회 항목을 찾을 수 없습니다."));
    }
}
