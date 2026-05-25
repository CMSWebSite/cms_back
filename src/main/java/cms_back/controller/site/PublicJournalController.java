package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicJournalDtos;
import cms_back.service.site.PublicJournalService;

@RestController
@RequestMapping("/api/public/journals")
public class PublicJournalController {

    private final PublicJournalService service;

    public PublicJournalController(PublicJournalService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<PublicJournalDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, sort);
    }

    @GetMapping("/{id}")
    public PublicJournalDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }
}
