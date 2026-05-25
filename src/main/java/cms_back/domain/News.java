package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@SQLDelete(sql = "UPDATE news SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class News {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 500)
    private String coverImageUrl;

    @Column(nullable = false)
    private LocalDateTime publishedAt;

    @Column(nullable = false)
    private boolean visible;

    private LocalDateTime deletedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 120)
    private String createdBy;

    @Column(length = 120)
    private String updatedBy;

    protected News() {}

    public static News create(String title, String content, String coverImageUrl,
                              LocalDateTime publishedAt, boolean visible, String actor) {
        News n = new News();
        n.title = title;
        n.content = content;
        n.coverImageUrl = coverImageUrl;
        n.publishedAt = publishedAt;
        n.visible = visible;
        n.createdAt = LocalDateTime.now();
        n.updatedAt = n.createdAt;
        n.createdBy = actor;
        n.updatedBy = actor;
        return n;
    }

    public void update(String title, String content, String coverImageUrl,
                       LocalDateTime publishedAt, boolean visible, String actor) {
        this.title = title;
        this.content = content;
        this.coverImageUrl = coverImageUrl;
        this.publishedAt = publishedAt;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
