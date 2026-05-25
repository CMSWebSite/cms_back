package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import cms_back.domain.converter.StringListConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 연구실 교수 프로필. 운영상 단일 레코드를 가정하며, 여러 행이 있을 경우
 * 공개 API는 id 오름차순의 첫 번째 visible 레코드만 반환한다.
 */
@Entity
@Table(name = "professors")
@SQLDelete(sql = "UPDATE professors SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Professor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 200)
    private String position;

    @Column(length = 500)
    private String profileImage;

    @Column(length = 200)
    private String email;

    @Column(length = 50)
    private String phone;

    @Column(length = 200)
    private String office;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @Convert(converter = StringListConverter.class)
    @Column(name = "research_fields", columnDefinition = "TEXT")
    private List<String> researchFields = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "education", columnDefinition = "TEXT")
    private List<String> education = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "career", columnDefinition = "TEXT")
    private List<String> career = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "publications", columnDefinition = "TEXT")
    private List<String> publications = new ArrayList<>();

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

    protected Professor() {}

    public static Professor createDefault(String actor) {
        Professor p = new Professor();
        p.name = "이광일";
        p.position = "Professor";
        p.visible = true;
        p.createdAt = LocalDateTime.now();
        p.updatedAt = p.createdAt;
        p.createdBy = actor;
        p.updatedBy = actor;
        return p;
    }

    public void update(String name, String position, String profileImage,
                       String email, String phone, String office, String biography,
                       List<String> researchFields, List<String> education,
                       List<String> career, List<String> publications,
                       boolean visible, String actor) {
        this.name = name;
        this.position = position;
        this.profileImage = profileImage;
        this.email = email;
        this.phone = phone;
        this.office = office;
        this.biography = biography;
        this.researchFields = researchFields != null ? new ArrayList<>(researchFields) : new ArrayList<>();
        this.education = education != null ? new ArrayList<>(education) : new ArrayList<>();
        this.career = career != null ? new ArrayList<>(career) : new ArrayList<>();
        this.publications = publications != null ? new ArrayList<>(publications) : new ArrayList<>();
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public String getProfileImage() { return profileImage; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getOffice() { return office; }
    public String getBiography() { return biography; }
    public List<String> getResearchFields() { return researchFields; }
    public List<String> getEducation() { return education; }
    public List<String> getCareer() { return career; }
    public List<String> getPublications() { return publications; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
