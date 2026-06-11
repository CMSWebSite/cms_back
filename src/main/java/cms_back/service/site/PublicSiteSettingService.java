package cms_back.service.site;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.repository.SiteSettingRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PublicSiteSettingService {

    private final SiteSettingRepository repository;

    public PublicSiteSettingService(SiteSettingRepository repository) {
        this.repository = repository;
    }

    /** 전체 설정을 key→value 맵으로 반환. 프론트에서 필요한 키만 골라 사용한다. */
    @Transactional(readOnly = true)
    public Map<String, String> all() {
        Map<String, String> result = new LinkedHashMap<>();
        repository.findAllByOrderByKeyAsc()
                .forEach(s -> result.put(s.getKey(), s.getValue()));
        return result;
    }
}
