package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.GalleryDtos;
import cms_back.dto.common.PageResponse;
import cms_back.service.admin.AdminGalleryService;

@RestController
@RequestMapping("/api/admin/gallery")
public class AdminGalleryController {

    private final AdminGalleryService service;

    public AdminGalleryController(AdminGalleryService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<GalleryDtos.AlbumSummary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, sort);
    }

    @GetMapping("/{id}")
    public GalleryDtos.AlbumDetail get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GalleryDtos.AlbumDetail create(@Valid @RequestBody GalleryDtos.AlbumUpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public GalleryDtos.AlbumDetail update(@PathVariable Long id,
                                          @Valid @RequestBody GalleryDtos.AlbumUpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
