package cms_back.controller.site;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.site.PublicContactDtos;
import cms_back.service.site.PublicContactService;

@RestController
@RequestMapping("/api/public/contact-applications")
public class PublicContactController {

    private final PublicContactService service;

    public PublicContactController(PublicContactService service) {
        this.service = service;
    }

    /** 비로그인 사용자가 Contact us 지원 정보를 제출한다. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublicContactDtos.CreateResponse create(@Valid @RequestBody PublicContactDtos.CreateRequest req) {
        return service.create(req);
    }
}
