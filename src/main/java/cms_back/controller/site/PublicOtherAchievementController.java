package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicOtherAchievementDtos;
import cms_back.service.site.PublicOtherAchievementService;

@RestController
@RequestMapping("/api/public/others")
public class PublicOtherAchievementController {

    private final PublicOtherAchievementService service;

    public PublicOtherAchievementController(PublicOtherAchievementService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<PublicOtherAchievementDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, type, sort);
    }

    @GetMapping("/{id}")
    public PublicOtherAchievementDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }
}
