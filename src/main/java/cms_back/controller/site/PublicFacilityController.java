package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.site.PublicFacilityDtos;
import cms_back.service.site.PublicFacilityService;

import java.util.List;

@RestController
@RequestMapping("/api/public/facilities")
public class PublicFacilityController {

    private final PublicFacilityService service;

    public PublicFacilityController(PublicFacilityService service) {
        this.service = service;
    }

    @GetMapping
    public List<PublicFacilityDtos.Item> list() {
        return service.list();
    }
}
