package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.site.PublicVisionMissionDtos;
import cms_back.service.site.PublicVisionMissionService;

import java.util.List;

@RestController
@RequestMapping("/api/public/vision-mission")
public class PublicVisionMissionController {

    private final PublicVisionMissionService service;

    public PublicVisionMissionController(PublicVisionMissionService service) {
        this.service = service;
    }

    @GetMapping
    public List<PublicVisionMissionDtos.Section> list() { return service.list(); }
}
