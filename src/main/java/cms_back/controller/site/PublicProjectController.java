package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.domain.ProjectStatus;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicProjectDtos;
import cms_back.service.site.PublicProjectService;

@RestController
@RequestMapping("/api/public/projects")
public class PublicProjectController {

    private final PublicProjectService service;

    public PublicProjectController(PublicProjectService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<PublicProjectDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, status, sort);
    }

    @GetMapping("/{id}")
    public PublicProjectDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }
}
