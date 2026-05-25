package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patents")
@SQLDelete(sql = "UPDATE patents SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Patent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 1000)
    private String inventors;

    @Column(length = 100)
    private String applicationNumber;

    @Column(length = 100)
    private String registrationNumber;

    private LocalDate applicationDate;
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PatentStatus status;

    @Column(name = "abstract_text", columnDefinition = "TEXT")
    private String abstractText;

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

    protected Patent() {}

    public static Patent create(String title, String inventors,
                                String applicationNumber, String registrationNumber,
                                LocalDate applicationDate, LocalDate registrationDate,
                                PatentStatus status, String abstractText,
                                boolean visible, String actor) {
        Patent p = new Patent();
        p.title = title;
        p.inventors = inventors;
        p.applicationNumber = applicationNumber;
        p.registrationNumber = registrationNumber;
        p.applicationDate = applicationDate;
        p.registrationDate = registrationDate;
        p.status = status;
        p.abstractText = abstractText;
        p.visible = visible;
        p.createdAt = LocalDateTime.now();
        p.updatedAt = p.createdAt;
        p.createdBy = actor;
        p.updatedBy = actor;
        return p;
    }

    public void update(String title, String inventors,
                       String applicationNumber, String registrationNumber,
                       LocalDate applicationDate, LocalDate registrationDate,
                       PatentStatus status, String abstractText,
                       boolean visible, String actor) {
        this.title = title;
        this.inventors = inventors;
        this.applicationNumber = applicationNumber;
        this.registrationNumber = registrationNumber;
        this.applicationDate = applicationDate;
        this.registrationDate = registrationDate;
        this.status = status;
        this.abstractText = abstractText;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    /** 목록 정렬 기준이 되는 대표일: 등록일이 있으면 등록일, 없으면 출원일. */
    public LocalDate getRepresentativeDate() {
        return registrationDate != null ? registrationDate : applicationDate;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getInventors() { return inventors; }
    public String getApplicationNumber() { return applicationNumber; }
    public String getRegistrationNumber() { return registrationNumber; }
    public LocalDate getApplicationDate() { return applicationDate; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public PatentStatus getStatus() { return status; }
    public String getAbstractText() { return abstractText; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
