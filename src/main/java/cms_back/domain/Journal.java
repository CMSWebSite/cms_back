package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import cms_back.domain.converter.StringListConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "journals")
@SQLDelete(sql = "UPDATE journals SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Journal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title1;

    @Column(length = 300)
    private String title2;

    @Column(nullable = false)
    private LocalDate publishedDate;

    @Column(nullable = false, length = 1000)
    private String authors;

    // ▼ Phase A 신규: 학술지 메타 정형 필드. 기존 metaLines 와 병행 운영(점진적 마이그레이션).
    @Column(length = 300)
    private String journalName;

    @Column(length = 50)
    private String volume;

    @Column(length = 50)
    private String issue;

    @Column(length = 50)
    private String pages;

    @Column(length = 200)
    private String doi;

    @Convert(converter = StringListConverter.class)
    @Column(name = "meta_lines", columnDefinition = "TEXT")
    private List<String> metaLines = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "highlights", columnDefinition = "TEXT")
    private List<String> highlights = new ArrayList<>();

    @Column(name = "abstract_text", columnDefinition = "TEXT")
    private String abstractText;

    @Column(length = 300)
    private String attachmentName;

    @Column(length = 500)
    private String attachmentUrl;

    private Long attachmentSize;

    /** 공개 사이트에 노출할지 여부. 기존 행은 DB DEFAULT 1 로 채워 자동 노출된다. */
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

    protected Journal() {}

    public static Journal create(String title1, String title2, LocalDate publishedDate, String authors,
                                 String journalName, String volume, String issue, String pages, String doi,
                                 List<String> metaLines, List<String> highlights, String abstractText,
                                 String attachmentName, String attachmentUrl, Long attachmentSize,
                                 boolean visible, String actor) {
        Journal j = new Journal();
        j.title1 = title1;
        j.title2 = title2;
        j.publishedDate = publishedDate;
        j.authors = authors;
        j.journalName = journalName;
        j.volume = volume;
        j.issue = issue;
        j.pages = pages;
        j.doi = doi;
        j.metaLines = metaLines != null ? new ArrayList<>(metaLines) : new ArrayList<>();
        j.highlights = highlights != null ? new ArrayList<>(highlights) : new ArrayList<>();
        j.abstractText = abstractText;
        j.attachmentName = attachmentName;
        j.attachmentUrl = attachmentUrl;
        j.attachmentSize = attachmentSize;
        j.visible = visible;
        j.createdAt = LocalDateTime.now();
        j.updatedAt = j.createdAt;
        j.createdBy = actor;
        j.updatedBy = actor;
        return j;
    }

    public void update(String title1, String title2, LocalDate publishedDate, String authors,
                       String journalName, String volume, String issue, String pages, String doi,
                       List<String> metaLines, List<String> highlights, String abstractText,
                       String attachmentName, String attachmentUrl, Long attachmentSize,
                       boolean visible, String actor) {
        this.title1 = title1;
        this.title2 = title2;
        this.publishedDate = publishedDate;
        this.authors = authors;
        this.journalName = journalName;
        this.volume = volume;
        this.issue = issue;
        this.pages = pages;
        this.doi = doi;
        this.metaLines = metaLines != null ? new ArrayList<>(metaLines) : new ArrayList<>();
        this.highlights = highlights != null ? new ArrayList<>(highlights) : new ArrayList<>();
        this.abstractText = abstractText;
        this.attachmentName = attachmentName;
        this.attachmentUrl = attachmentUrl;
        this.attachmentSize = attachmentSize;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getTitle1() { return title1; }
    public String getTitle2() { return title2; }
    public LocalDate getPublishedDate() { return publishedDate; }
    public String getAuthors() { return authors; }
    public String getJournalName() { return journalName; }
    public String getVolume() { return volume; }
    public String getIssue() { return issue; }
    public String getPages() { return pages; }
    public String getDoi() { return doi; }
    public List<String> getMetaLines() { return metaLines; }
    public List<String> getHighlights() { return highlights; }
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
