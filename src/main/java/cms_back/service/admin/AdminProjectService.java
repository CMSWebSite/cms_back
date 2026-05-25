package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Project;
import cms_back.domain.ProjectStatus;
import cms_back.dto.admin.ProjectDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.ProjectRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class AdminProjectService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "startDate");

    private final ProjectRepository repository;
    private final AdminContext adminContext;

    public AdminProjectService(ProjectRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public PageResponse<ProjectDtos.Summary> list(int page, int limit, String keyword,
                                                  ProjectStatus status, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(null, status, keyword, pageable),
                ProjectDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public ProjectDtos.Detail get(Long id) {
        return ProjectDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public ProjectDtos.Detail create(ProjectDtos.UpsertRequest req) {
        Project saved = repository.save(Project.create(
                req.title(), req.description(),
                req.startDate(), req.endDate(),
                req.fundingAgency(), req.role(), req.status(),
                req.thumbnailImage(), req.detailContent(),
                req.visible(),
                adminContext.currentActor()
        ));
        return ProjectDtos.Detail.from(saved);
    }

    @Transactional
    public ProjectDtos.Detail update(Long id, ProjectDtos.UpsertRequest req) {
        Project p = findOrThrow(id);
        p.update(req.title(), req.description(),
                req.startDate(), req.endDate(),
                req.fundingAgency(), req.role(), req.status(),
                req.thumbnailImage(), req.detailContent(),
                req.visible(),
                adminContext.currentActor());
        return ProjectDtos.Detail.from(p);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Project findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 프로젝트를 찾을 수 없습니다."));
    }
}
