package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.domain.PatentStatus;
import cms_back.dto.admin.PatentDtos;
import cms_back.dto.common.PageResponse;
import cms_back.service.admin.AdminPatentService;

@RestController
@RequestMapping("/api/admin/patents")
public class AdminPatentController {

    private final AdminPatentService service;

    public AdminPatentController(AdminPatentService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<PatentDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) PatentStatus status,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, status, sort);
    }

    @GetMapping("/{id}")
    public PatentDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatentDtos.Detail create(@Valid @RequestBody PatentDtos.UpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public PatentDtos.Detail update(@PathVariable Long id,
                                    @Valid @RequestBody PatentDtos.UpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
