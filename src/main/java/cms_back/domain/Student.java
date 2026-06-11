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
@Table(
        name = "students",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_students_slug", columnNames = "slug")
        }
)
@SQLDelete(sql = "UPDATE students SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String slug;

    @Column(nullable = false, length = 50)
    private String koreanName;

    @Column(nullable = false, length = 100)
    private String englishName;

    @Column(nullable = false)
    private LocalDate enrolledAt;

    @Column(nullable = false, length = 100)
    private String role;

    @Convert(converter = StringListConverter.class)
    @Column(name = "majors", columnDefinition = "TEXT")
    private List<String> majors = new ArrayList<>();

    // ▼ Phase C 신규 필드
    @Convert(converter = StringListConverter.class)
    @Column(name = "research_interests", columnDefinition = "TEXT")
    private List<String> researchInterests = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "career", columnDefinition = "TEXT")
    private List<String> career = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "publications", columnDefinition = "TEXT")
    private List<String> publications = new ArrayList<>();

    @Column(length = 200)
    private String email;

    @Column(length = 500)
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StudentStatus status;

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

    protected Student() {}

    public static Student create(String slug, String koreanName, String englishName,
                                 LocalDate enrolledAt, String role, List<String> majors,
                                 List<String> researchInterests, List<String> career,
                                 List<String> publications, String email,
                                 String photoUrl, StudentStatus status,
                                 boolean visible, String actor) {
        Student s = new Student();
        s.slug = slug;
        s.koreanName = koreanName;
        s.englishName = englishName;
        s.enrolledAt = enrolledAt;
        s.role = role;
        s.majors = majors != null ? new ArrayList<>(majors) : new ArrayList<>();
        s.researchInterests = researchInterests != null ? new ArrayList<>(researchInterests) : new ArrayList<>();
        s.career = career != null ? new ArrayList<>(career) : new ArrayList<>();
        s.publications = publications != null ? new ArrayList<>(publications) : new ArrayList<>();
        s.email = email;
        s.photoUrl = photoUrl;
        s.status = status;
        s.visible = visible;
        s.createdAt = LocalDateTime.now();
        s.updatedAt = s.createdAt;
        s.createdBy = actor;
        s.updatedBy = actor;
        return s;
    }

    public void update(String slug, String koreanName, String englishName,
                       LocalDate enrolledAt, String role, List<String> majors,
                       List<String> researchInterests, List<String> career,
                       List<String> publications, String email,
                       String photoUrl, StudentStatus status,
                       boolean visible, String actor) {
        this.slug = slug;
        this.koreanName = koreanName;
        this.englishName = englishName;
        this.enrolledAt = enrolledAt;
        this.role = role;
        this.majors = majors != null ? new ArrayList<>(majors) : new ArrayList<>();
        this.researchInterests = researchInterests != null ? new ArrayList<>(researchInterests) : new ArrayList<>();
        this.career = career != null ? new ArrayList<>(career) : new ArrayList<>();
        this.publications = publications != null ? new ArrayList<>(publications) : new ArrayList<>();
        this.email = email;
        this.photoUrl = photoUrl;
        this.status = status;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getSlug() { return slug; }
    public String getKoreanName() { return koreanName; }
    public String getEnglishName() { return englishName; }
    public LocalDate getEnrolledAt() { return enrolledAt; }
    public String getRole() { return role; }
    public List<String> getMajors() { return majors; }
    public List<String> getResearchInterests() { return researchInterests; }
    public List<String> getCareer() { return career; }
    public List<String> getPublications() { return publications; }
    public String getEmail() { return email; }
    public String getPhotoUrl() { return photoUrl; }
    public StudentStatus getStatus() { return status; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
