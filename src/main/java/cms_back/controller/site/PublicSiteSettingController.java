package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.service.site.PublicSiteSettingService;

import java.util.Map;

@RestController
@RequestMapping("/api/public/site-settings")
public class PublicSiteSettingController {

    private final PublicSiteSettingService service;

    public PublicSiteSettingController(PublicSiteSettingService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, String> all() {
        return service.all();
    }
}
