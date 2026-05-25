package cms_back.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.SiteSetting;
import cms_back.repository.SiteSettingRepository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 부팅 시 SiteSetting 기본 키를 멱등하게 시드한다.
 * 이미 존재하는 키는 건너뛰므로 운영자가 변경한 값을 덮어쓰지 않는다.
 *
 * Phase E에서 Homepage Hero / Research / Footer / SEO / Logo 키 추가.
 */
@Component
@Order(10) // StudentStatusMigrationRunner(0) 이후 실행
public class SiteSettingSeedRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SiteSettingSeedRunner.class);

    private final SiteSettingRepository repository;

    public SiteSettingSeedRunner(SiteSettingRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Map<String, SeedEntry> defaults = buildDefaults();

        int inserted = 0;
        for (Map.Entry<String, SeedEntry> entry : defaults.entrySet()) {
            String key = entry.getKey();
            SeedEntry seed = entry.getValue();
            if (!repository.existsByKey(key)) {
                repository.save(SiteSetting.create(
                        key, seed.value, seed.type, seed.description, "system-seed"
                ));
                inserted++;
            }
        }
        if (inserted > 0) {
            log.info("[SiteSettingSeed] {} 개 기본 키를 시드했습니다.", inserted);
        }
    }

    private record SeedEntry(String value, String type, String description) {}

    private static Map<String, SeedEntry> buildDefaults() {
        Map<String, SeedEntry> m = new LinkedHashMap<>();

        // Contact us
        m.put("contact.email",
                new SeedEntry("", "email", "Contact us 페이지 이메일"));
        m.put("contact.phone",
                new SeedEntry("", "text", "Contact us 페이지 전화번호"));
        m.put("contact.address",
                new SeedEntry("(49112) 부산광역시 영도구 태종로 727(동삼동) 한국해양대학교 공과대학 2호관 638호",
                        "text", "Contact us 페이지 주소"));
        m.put("contact.mapUrl",
                new SeedEntry("", "url", "Google Maps embed URL (선택)"));

        // Homepage Hero
        m.put("homepage.hero.image",
                new SeedEntry("", "url", "Homepage Hero GIF/이미지 URL"));
        m.put("homepage.hero.subtitle",
                new SeedEntry(
                        "Our lab focuses on engineering intelligence,\nmoving beyond isolated models toward real-world systems.",
                        "text", "Homepage Hero 하단 캡션"));

        // Homepage Research section (3 cards)
        m.put("homepage.research.card1.title",
                new SeedEntry("Innovative Battlefield Solutions\nEnabled by Artificial Intelligence",
                        "text", "Research 카드 1 제목"));
        m.put("homepage.research.card1.image",
                new SeedEntry("", "url", "Research 카드 1 이미지"));
        m.put("homepage.research.card2.title",
                new SeedEntry("Development of Standardized\nDocumentation",
                        "text", "Research 카드 2 제목"));
        m.put("homepage.research.card2.image",
                new SeedEntry("", "url", "Research 카드 2 이미지"));
        m.put("homepage.research.card3.title",
                new SeedEntry("Smart Ship and\nMarine Cybersecurity",
                        "text", "Research 카드 3 제목"));
        m.put("homepage.research.card3.image",
                new SeedEntry("", "url", "Research 카드 3 이미지"));

        // Footer
        m.put("footer.phone",
                new SeedEntry("+82 010-XXXX-XXXX", "text", "Footer 전화번호"));
        m.put("footer.fax",
                new SeedEntry("+82 010-XXXX-XXXX", "text", "Footer FAX"));
        m.put("footer.address",
                new SeedEntry("(49112) 부산광역시 영도구 태종로 727(동삼동) 한국해양대학교 공과대학 2호관 638호",
                        "text", "Footer 주소"));
        m.put("footer.copyright",
                new SeedEntry("Copyright © 2026 Cybermarine System Lab. All Rights Reserved.",
                        "text", "Footer 저작권 문구"));

        // Site / SEO
        m.put("site.logoUrl",
                new SeedEntry("", "url", "사이트 로고 이미지 (선택)"));
        m.put("site.title",
                new SeedEntry("Cybermarine System Lab", "text", "사이트 제목"));
        m.put("seo.description",
                new SeedEntry("한국해양대학교 사이버마린 시스템 연구실 공식 웹사이트", "text", "SEO 설명"));
        m.put("seo.ogImage",
                new SeedEntry("", "url", "Open Graph 공유 이미지 URL"));

        return m;
    }
}
