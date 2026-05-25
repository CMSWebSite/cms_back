package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.FacilityDtos;
import cms_back.dto.common.PageResponse;
import cms_back.service.admin.AdminFacilityService;

@RestController
@RequestMapping("/api/admin/facilities")
public class AdminFacilityController {

    private final AdminFacilityService service;

    public AdminFacilityController(AdminFacilityService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<FacilityDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, category, sort);
    }

    @GetMapping("/{id}")
    public FacilityDtos.Detail get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityDtos.Detail create(@Valid @RequestBody FacilityDtos.UpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public FacilityDtos.Detail update(@PathVariable Long id,
                                      @Valid @RequestBody FacilityDtos.UpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
