package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.domain.PatentStatus;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicPatentDtos;
import cms_back.service.site.PublicPatentService;

@RestController
@RequestMapping("/api/public/patents")
public class PublicPatentController {

    private final PublicPatentService service;

    public PublicPatentController(PublicPatentService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<PublicPatentDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) PatentStatus status,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, status, sort);
    }

    @GetMapping("/{id}")
    public PublicPatentDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }
}
