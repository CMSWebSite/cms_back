package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@SQLDelete(sql = "UPDATE projects SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(length = 200)
    private String fundingAgency;

    /** 예: "주관"/"참여"/"책임"  자유 텍스트로 둔다. */
    @Column(length = 100)
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus status;

    @Column(length = 500)
    private String thumbnailImage;

    @Column(name = "detail_content", columnDefinition = "TEXT")
    private String detailContent;

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

    protected Project() {}

    public static Project create(String title, String description,
                                 LocalDate startDate, LocalDate endDate,
                                 String fundingAgency, String role, ProjectStatus status,
                                 String thumbnailImage, String detailContent,
                                 boolean visible, String actor) {
        Project p = new Project();
        p.title = title;
        p.description = description;
        p.startDate = startDate;
        p.endDate = endDate;
        p.fundingAgency = fundingAgency;
        p.role = role;
        p.status = status;
        p.thumbnailImage = thumbnailImage;
        p.detailContent = detailContent;
        p.visible = visible;
        p.createdAt = LocalDateTime.now();
        p.updatedAt = p.createdAt;
        p.createdBy = actor;
        p.updatedBy = actor;
        return p;
    }

    public void update(String title, String description,
                       LocalDate startDate, LocalDate endDate,
                       String fundingAgency, String role, ProjectStatus status,
                       String thumbnailImage, String detailContent,
                       boolean visible, String actor) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fundingAgency = fundingAgency;
        this.role = role;
        this.status = status;
        this.thumbnailImage = thumbnailImage;
        this.detailContent = detailContent;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getFundingAgency() { return fundingAgency; }
    public String getRole() { return role; }
    public ProjectStatus getStatus() { return status; }
    public String getThumbnailImage() { return thumbnailImage; }
    public String getDetailContent() { return detailContent; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
