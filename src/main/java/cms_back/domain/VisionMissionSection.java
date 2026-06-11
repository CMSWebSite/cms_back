package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import cms_back.domain.converter.StringListConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Vision & Mission 페이지의 각 섹션. sectionKey로 의미 식별 (intro / area1 / area2 / area3 / closing 등),
 * sortOrder로 노출 순서 결정. content는 본문(plain/markdown), items는 번호 목록(선택).
 */
@Entity
@Table(
        name = "vision_mission_sections",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_vm_section_key", columnNames = "section_key")
        }
)
@SQLDelete(sql = "UPDATE vision_mission_sections SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class VisionMissionSection {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "section_key", nullable = false, length = 100)
    private String sectionKey;

    @Column(length = 300)
    private String title;

    @Column(length = 300)
    private String subtitle;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Convert(converter = StringListConverter.class)
    @Column(name = "items", columnDefinition = "TEXT")
    private List<String> items = new ArrayList<>();

    @Column(length = 500)
    private String image;

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

    protected VisionMissionSection() {}

    public static VisionMissionSection create(String sectionKey, String title, String subtitle,
                                              String content, List<String> items, String image,
                                              int sortOrder, boolean visible, String actor) {
        VisionMissionSection v = new VisionMissionSection();
        v.sectionKey = sectionKey;
        v.title = title;
        v.subtitle = subtitle;
        v.content = content;
        v.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        v.image = image;
        v.sortOrder = sortOrder;
        v.visible = visible;
        v.createdAt = LocalDateTime.now();
        v.updatedAt = v.createdAt;
        v.createdBy = actor;
        v.updatedBy = actor;
        return v;
    }

    public void update(String sectionKey, String title, String subtitle,
                       String content, List<String> items, String image,
                       int sortOrder, boolean visible, String actor) {
        this.sectionKey = sectionKey;
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.image = image;
        this.sortOrder = sortOrder;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getSectionKey() { return sectionKey; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getContent() { return content; }
    public List<String> getItems() { return items; }
    public String getImage() { return image; }
    public int getSortOrder() { return sortOrder; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
