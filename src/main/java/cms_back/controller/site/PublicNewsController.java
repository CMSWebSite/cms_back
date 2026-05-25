package cms_back.controller.site;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.site.PublicNewsDtos;
import cms_back.service.site.PublicNewsService;

import java.util.List;

/**
 * 일반 사용자가 읽는 News 엔드포인트(인증 불필요).
 */
@RestController
@RequestMapping("/api/public/news")
public class PublicNewsController {

    private final PublicNewsService service;

    public PublicNewsController(PublicNewsService service) {
        this.service = service;
    }

    @GetMapping
    public List<PublicNewsDtos.Summary> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public PublicNewsDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }
}
