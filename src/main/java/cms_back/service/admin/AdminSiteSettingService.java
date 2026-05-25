package cms_back.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.SiteSetting;
import cms_back.dto.admin.SiteSettingDtos;
import cms_back.repository.SiteSettingRepository;

import java.util.List;

@Service
public class AdminSiteSettingService {

    private final SiteSettingRepository repository;
    private final AdminContext adminContext;

    public AdminSiteSettingService(SiteSettingRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public List<SiteSettingDtos.Item> list() {
        return repository.findAllByOrderByKeyAsc().stream()
                .map(SiteSettingDtos.Item::from)
                .toList();
    }

    @Transactional
    public SiteSettingDtos.Item upsert(SiteSettingDtos.UpsertRequest req) {
        SiteSetting s = repository.findByKey(req.key())
                .map(existing -> {
                    existing.updateValue(req.value(), req.type(), req.description(),
                            adminContext.currentActor());
                    return existing;
                })
                .orElseGet(() -> repository.save(SiteSetting.create(
                        req.key(), req.value(), req.type(), req.description(),
                        adminContext.currentActor()
                )));
        return SiteSettingDtos.Item.from(s);
    }

    @Transactional
    public List<SiteSettingDtos.Item> bulkUpsert(SiteSettingDtos.BulkUpsertRequest req) {
        if (req.settings() == null) return List.of();
        return req.settings().stream().map(this::upsert).toList();
    }
}
