package cms_back.service.site;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Project;
import cms_back.domain.ProjectStatus;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicProjectDtos;
import cms_back.repository.ProjectRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class PublicProjectService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "startDate");

    private final ProjectRepository repository;

    public PublicProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PageResponse<PublicProjectDtos.Summary> list(int page, int limit, String keyword,
                                                        ProjectStatus status, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(true, status, keyword, pageable),
                PublicProjectDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public PublicProjectDtos.Detail get(Long id) {
        Project p = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 프로젝트를 찾을 수 없습니다."));
        if (!p.isVisible()) {
            throw new NotFoundException("해당 프로젝트를 찾을 수 없습니다.");
        }
        return PublicProjectDtos.Detail.from(p);
    }
}
