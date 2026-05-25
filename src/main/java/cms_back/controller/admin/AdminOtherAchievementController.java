package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.OtherAchievementDtos;
import cms_back.dto.common.PageResponse;
import cms_back.service.admin.AdminOtherAchievementService;

@RestController
@RequestMapping("/api/admin/others")
public class AdminOtherAchievementController {

    private final AdminOtherAchievementService service;

    public AdminOtherAchievementController(AdminOtherAchievementService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<OtherAchievementDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, type, sort);
    }

    @GetMapping("/{id}")
    public OtherAchievementDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OtherAchievementDtos.Detail create(@Valid @RequestBody OtherAchievementDtos.UpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public OtherAchievementDtos.Detail update(@PathVariable Long id,
                                              @Valid @RequestBody OtherAchievementDtos.UpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
