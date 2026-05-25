package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicGalleryDtos;
import cms_back.service.site.PublicGalleryService;

@RestController
@RequestMapping("/api/public/gallery")
public class PublicGalleryController {

    private final PublicGalleryService service;

    public PublicGalleryController(PublicGalleryService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<PublicGalleryDtos.AlbumSummary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, sort);
    }

    @GetMapping("/{id}")
    public PublicGalleryDtos.AlbumDetail get(@PathVariable Long id) {
        return service.get(id);
    }
}
