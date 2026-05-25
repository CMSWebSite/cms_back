package cms_back.controller.site;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicQnaDtos;
import cms_back.service.site.PublicQnaService;

@RestController
@RequestMapping("/api/public/qna")
public class PublicQnaController {

    private final PublicQnaService service;

    public PublicQnaController(PublicQnaService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<PublicQnaDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, sort);
    }

    @GetMapping("/{id}")
    public PublicQnaDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }

    /** 비로그인 사용자가 문의를 작성한다. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublicQnaDtos.Detail create(@Valid @RequestBody PublicQnaDtos.CreateRequest req) {
        return service.create(req);
    }
}
