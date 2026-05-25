package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.QnaDtos;
import cms_back.dto.common.PageResponse;
import cms_back.service.admin.AdminQnaService;

@RestController
@RequestMapping("/api/admin/qna")
public class AdminQnaController {

    private final AdminQnaService service;

    public AdminQnaController(AdminQnaService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<QnaDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean answered,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, answered, sort);
    }

    @GetMapping("/{id}")
    public QnaDtos.Detail get(@PathVariable Long id) { return service.get(id); }

    @PutMapping("/{id}")
    public QnaDtos.Detail update(@PathVariable Long id,
                                 @Valid @RequestBody QnaDtos.AdminUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
