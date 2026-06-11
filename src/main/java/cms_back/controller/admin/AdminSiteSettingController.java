package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.SiteSettingDtos;
import cms_back.service.admin.AdminSiteSettingService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/site-settings")
public class AdminSiteSettingController {

    private final AdminSiteSettingService service;

    public AdminSiteSettingController(AdminSiteSettingService service) {
        this.service = service;
    }

    @GetMapping
    public List<SiteSettingDtos.Item> list() { return service.list(); }

    /** 단일 key upsert. */
    @PutMapping
    public SiteSettingDtos.Item upsert(@Valid @RequestBody SiteSettingDtos.UpsertRequest req) {
        return service.upsert(req);
    }

    /** 여러 key 한번에 저장 (ContactUs 등). */
    @PutMapping("/bulk")
    public List<SiteSettingDtos.Item> bulkUpsert(@RequestBody SiteSettingDtos.BulkUpsertRequest req) {
        return service.bulkUpsert(req);
    }
}
