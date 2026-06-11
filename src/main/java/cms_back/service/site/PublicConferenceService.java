package cms_back.service.site;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Conference;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicConferenceDtos;
import cms_back.repository.ConferenceRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class PublicConferenceService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "presentedDate");

    private final ConferenceRepository repository;

    public PublicConferenceService(ConferenceRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PageResponse<PublicConferenceDtos.Summary> list(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(true, keyword, pageable),
                PublicConferenceDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public PublicConferenceDtos.Detail get(Long id) {
        Conference c = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 학회 항목을 찾을 수 없습니다."));
        if (!c.isVisible()) {
            throw new NotFoundException("해당 학회 항목을 찾을 수 없습니다.");
        }
        return PublicConferenceDtos.Detail.from(c);
    }
}
