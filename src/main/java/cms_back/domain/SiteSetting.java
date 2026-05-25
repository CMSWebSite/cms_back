package cms_back.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 사이트 전역 설정을 key-value로 저장한다.
 * Phase D 사용 키:
 *   - contact.email, contact.phone, contact.address, contact.mapUrl
 * Phase E에서 Homepage Hero, Footer, Logo, SEO 등의 키 추가 예정.
 */
@Entity
@Table(
        name = "site_settings",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_site_setting_key", columnNames = "setting_key")
        }
)
public class SiteSetting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "setting_key", nullable = false, length = 100)
    private String key;

    @Column(columnDefinition = "TEXT")
    private String value;

    /** 값의 의미적 타입(예: "text", "url", "email", "json") — 단순 라벨. */
    @Column(length = 30)
    private String type;

    @Column(length = 300)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 120)
    private String updatedBy;

    protected SiteSetting() {}

    public static SiteSetting create(String key, String value, String type, String description, String actor) {
        SiteSetting s = new SiteSetting();
        s.key = key;
        s.value = value;
        s.type = type;
        s.description = description;
        s.createdAt = LocalDateTime.now();
        s.updatedAt = s.createdAt;
        s.updatedBy = actor;
        return s;
    }

    public void updateValue(String value, String type, String description, String actor) {
        this.value = value;
        if (type != null) this.type = type;
        if (description != null) this.description = description;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getKey() { return key; }
    public String getValue() { return value; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getUpdatedBy() { return updatedBy; }
}
