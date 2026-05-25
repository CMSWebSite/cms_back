package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.JournalDtos;
import cms_back.dto.common.PageResponse;
import cms_back.service.admin.AdminJournalService;

@RestController
@RequestMapping("/api/admin/journals")
public class AdminJournalController {

    private final AdminJournalService service;

    public AdminJournalController(AdminJournalService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<JournalDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, sort);
    }

    @GetMapping("/{id}")
    public JournalDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JournalDtos.Detail create(@Valid @RequestBody JournalDtos.UpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public JournalDtos.Detail update(@PathVariable Long id,
                                     @Valid @RequestBody JournalDtos.UpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
