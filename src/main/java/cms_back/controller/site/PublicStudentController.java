package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicStudentDtos;
import cms_back.service.site.PublicStudentService;

@RestController
@RequestMapping("/api/public")
public class PublicStudentController {

    private final PublicStudentService service;

    public PublicStudentController(PublicStudentService service) {
        this.service = service;
    }

    /** 재학생 목록. */
    @GetMapping("/students")
    public PageResponse<PublicStudentDtos.Summary> listStudents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.listActive(page, limit, keyword, sort);
    }

    /** 졸업생(Alumni) 목록. */
    @GetMapping("/alumni")
    public PageResponse<PublicStudentDtos.Summary> listAlumni(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.listAlumni(page, limit, keyword, sort);
    }

    /** slug 기반 단건 조회. */
    @GetMapping("/students/{slug}")
    public PublicStudentDtos.Detail getBySlug(@PathVariable String slug) {
        return service.getBySlug(slug);
    }
}
