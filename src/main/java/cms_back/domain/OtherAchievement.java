package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 저작권/기술이전/기타 분류의 자유 실적. type 필드로 카테고리를 표현한다.
 */
@Entity
@Table(name = "other_achievements")
@SQLDelete(sql = "UPDATE other_achievements SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class OtherAchievement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    /** 예: "저작권", "기술이전", "기타" — 자유 텍스트 */
    @Column(nullable = false, length = 50)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate achievedOn;

    @Column(length = 300)
    private String attachmentName;

    @Column(length = 500)
    private String attachmentUrl;

    private Long attachmentSize;

    @Column(nullable = false, columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1")
    private boolean visible = true;

    private LocalDateTime deletedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 120)
    private String createdBy;

    @Column(length = 120)
    private String updatedBy;

    protected OtherAchievement() {}

    public static OtherAchievement create(String title, String type, String description,
                                          LocalDate achievedOn,
                                          String attachmentName, String attachmentUrl,
                                          Long attachmentSize,
                                          boolean visible, String actor) {
        OtherAchievement o = new OtherAchievement();
        o.title = title;
        o.type = type;
        o.description = description;
        o.achievedOn = achievedOn;
        o.attachmentName = attachmentName;
        o.attachmentUrl = attachmentUrl;
        o.attachmentSize = attachmentSize;
        o.visible = visible;
        o.createdAt = LocalDateTime.now();
        o.updatedAt = o.createdAt;
        o.createdBy = actor;
        o.updatedBy = actor;
        return o;
    }

    public void update(String title, String type, String description,
                       LocalDate achievedOn,
                       String attachmentName, String attachmentUrl,
                       Long attachmentSize,
                       boolean visible, String actor) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.achievedOn = achievedOn;
        this.attachmentName = attachmentName;
        this.attachmentUrl = attachmentUrl;
        this.attachmentSize = attachmentSize;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public LocalDate getAchievedOn() { return achievedOn; }
    public String getAttachmentName() { return attachmentName; }
    public String getAttachmentUrl() { return attachmentUrl; }
    public Long getAttachmentSize() { return attachmentSize; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
