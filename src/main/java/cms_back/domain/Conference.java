package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "conferences")
@SQLDelete(sql = "UPDATE conferences SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Conference {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(nullable = false, length = 1000)
    private String authors;

    @Column(nullable = false, length = 300)
    private String conferenceName;

    @Column(length = 200)
    private String location;

    @Column(nullable = false)
    private LocalDate presentedDate;

    @Column(name = "abstract_text", columnDefinition = "TEXT")
    private String abstractText;

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

    protected Conference() {}

    public static Conference create(String title, String authors, String conferenceName,
                                    String location, LocalDate presentedDate, String abstractText,
                                    String attachmentName, String attachmentUrl, Long attachmentSize,
                                    boolean visible, String actor) {
        Conference c = new Conference();
        c.title = title;
        c.authors = authors;
        c.conferenceName = conferenceName;
        c.location = location;
        c.presentedDate = presentedDate;
        c.abstractText = abstractText;
        c.attachmentName = attachmentName;
        c.attachmentUrl = attachmentUrl;
        c.attachmentSize = attachmentSize;
        c.visible = visible;
        c.createdAt = LocalDateTime.now();
        c.updatedAt = c.createdAt;
        c.createdBy = actor;
        c.updatedBy = actor;
        return c;
    }

    public void update(String title, String authors, String conferenceName,
                       String location, LocalDate presentedDate, String abstractText,
                       String attachmentName, String attachmentUrl, Long attachmentSize,
                       boolean visible, String actor) {
        this.title = title;
        this.authors = authors;
        this.conferenceName = conferenceName;
        this.location = location;
        this.presentedDate = presentedDate;
        this.abstractText = abstractText;
        this.attachmentName = attachmentName;
        this.attachmentUrl = attachmentUrl;
        this.attachmentSize = attachmentSize;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthors() { return authors; }
    public String getConferenceName() { return conferenceName; }
    public String getLocation() { return location; }
    public LocalDate getPresentedDate() { return presentedDate; }
    public String getAbstractText() { return abstractText; }
    public String getAttachmentName() { return attachmentName; }
    public String getAttachmentUrl() { return attachmentUrl; }
    public Long getAttachmentSize() { return attachmentSize; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
