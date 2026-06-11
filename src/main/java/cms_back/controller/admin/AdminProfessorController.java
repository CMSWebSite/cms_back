package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.ProfessorDtos;
import cms_back.service.admin.AdminProfessorService;

/**
 * 교수 정보는 단일 레코드 패턴이므로 id 없이 GET/PUT 만 지원한다.
 */
@RestController
@RequestMapping("/api/admin/professor")
public class AdminProfessorController {

    private final AdminProfessorService service;

    public AdminProfessorController(AdminProfessorService service) {
        this.service = service;
    }

    @GetMapping
    public ProfessorDtos.Detail get() {
        return service.get();
    }

    @PutMapping
    public ProfessorDtos.Detail update(@Valid @RequestBody ProfessorDtos.UpsertRequest req) {
        return service.update(req);
    }
}
