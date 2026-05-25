package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicConferenceDtos;
import cms_back.service.site.PublicConferenceService;

@RestController
@RequestMapping("/api/public/conferences")
public class PublicConferenceController {

    private final PublicConferenceService service;

    public PublicConferenceController(PublicConferenceService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<PublicConferenceDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, sort);
    }

    @GetMapping("/{id}")
    public PublicConferenceDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }
}
