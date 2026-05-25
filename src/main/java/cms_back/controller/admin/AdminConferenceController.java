package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.ConferenceDtos;
import cms_back.dto.common.PageResponse;
import cms_back.service.admin.AdminConferenceService;

@RestController
@RequestMapping("/api/admin/conferences")
public class AdminConferenceController {

    private final AdminConferenceService service;

    public AdminConferenceController(AdminConferenceService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<ConferenceDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, sort);
    }

    @GetMapping("/{id}")
    public ConferenceDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConferenceDtos.Detail create(@Valid @RequestBody ConferenceDtos.UpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public ConferenceDtos.Detail update(@PathVariable Long id,
                                        @Valid @RequestBody ConferenceDtos.UpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
