package cms_back.service.site;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Patent;
import cms_back.domain.PatentStatus;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicPatentDtos;
import cms_back.repository.PatentRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class PublicPatentService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "applicationDate");

    private final PatentRepository repository;

    public PublicPatentService(PatentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PageResponse<PublicPatentDtos.Summary> list(int page, int limit, String keyword,
                                                       PatentStatus status, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(true, status, keyword, pageable),
                PublicPatentDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public PublicPatentDtos.Detail get(Long id) {
        Patent p = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 특허를 찾을 수 없습니다."));
        if (!p.isVisible()) {
            throw new NotFoundException("해당 특허를 찾을 수 없습니다.");
        }
        return PublicPatentDtos.Detail.from(p);
    }
}
