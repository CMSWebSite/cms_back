package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.site.PublicPartnerDtos;
import cms_back.service.site.PublicPartnerService;

import java.util.List;

@RestController
@RequestMapping("/api/public/partners")
public class PublicPartnerController {

    private final PublicPartnerService service;

    public PublicPartnerController(PublicPartnerService service) {
        this.service = service;
    }

    @GetMapping
    public List<PublicPartnerDtos.Item> list() {
        return service.list();
    }
}
