package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.domain.StudentStatus;
import cms_back.dto.admin.StudentDtos;
import cms_back.dto.common.PageResponse;
import cms_back.service.admin.AdminStudentService;

@RestController
@RequestMapping("/api/admin/students")
public class AdminStudentController {

    private final AdminStudentService service;

    public AdminStudentController(AdminStudentService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<StudentDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) StudentStatus status,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, status, sort);
    }

    @GetMapping("/{id}")
    public StudentDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDtos.Detail create(@Valid @RequestBody StudentDtos.UpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public StudentDtos.Detail update(@PathVariable Long id,
                                     @Valid @RequestBody StudentDtos.UpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
