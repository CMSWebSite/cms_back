package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.site.PublicProfessorDtos;
import cms_back.service.site.PublicProfessorService;

@RestController
@RequestMapping("/api/public/professor")
public class PublicProfessorController {

    private final PublicProfessorService service;

    public PublicProfessorController(PublicProfessorService service) {
        this.service = service;
    }

    @GetMapping
    public PublicProfessorDtos.Detail get() {
        return service.get();
    }
}
