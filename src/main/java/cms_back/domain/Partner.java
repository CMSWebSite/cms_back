package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

/**
 * 홈페이지 하단 Partners 섹션에 노출되는 협력 기관/파트너.
 */
@Entity
@Table(name = "partners")
@SQLDelete(sql = "UPDATE partners SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Partner {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 500)
    private String logoUrl;

    @Column(length = 500)
    private String websiteUrl;

    @Column(nullable = false)
    private int sortOrder = 0;

    @Column(nullable = false, columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1")
    private boolean visible = true;

    private LocalDateTime deletedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 120) private String createdBy;
    @Column(length = 120) private String updatedBy;

    protected Partner() {}

    public static Partner create(String name, String logoUrl, String websiteUrl,
                                 int sortOrder, boolean visible, String actor) {
        Partner p = new Partner();
        p.name = name;
        p.logoUrl = logoUrl;
        p.websiteUrl = websiteUrl;
        p.sortOrder = sortOrder;
        p.visible = visible;
        p.createdAt = LocalDateTime.now();
        p.updatedAt = p.createdAt;
        p.createdBy = actor;
        p.updatedBy = actor;
        return p;
    }

    public void update(String name, String logoUrl, String websiteUrl,
                       int sortOrder, boolean visible, String actor) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.websiteUrl = websiteUrl;
        this.sortOrder = sortOrder;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLogoUrl() { return logoUrl; }
    public String getWebsiteUrl() { return websiteUrl; }
    public int getSortOrder() { return sortOrder; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
