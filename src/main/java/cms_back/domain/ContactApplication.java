package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

/**
 * Contact us 페이지에서 비로그인 사용자가 제출한 지원(문의) 정보.
 * 관리자만 열람하며, 삭제는 소프트 삭제로 처리한다.
 */
@Entity
@Table(name = "contact_applications")
@SQLDelete(sql = "UPDATE contact_applications SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ContactApplication {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    /** 이메일 또는 전화번호 (사용자가 둘 중 하나를 자유 입력). */
    @Column(nullable = false, length = 200)
    private String contact;

    @Column(nullable = false, length = 200)
    private String major;

    @Column(columnDefinition = "TEXT")
    private String introduce;

    private LocalDateTime deletedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected ContactApplication() {}

    public static ContactApplication createFromUser(String name, String contact,
                                                    String major, String introduce) {
        ContactApplication c = new ContactApplication();
        c.name = name;
        c.contact = contact;
        c.major = major;
        c.introduce = introduce;
        c.createdAt = LocalDateTime.now();
        return c;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getMajor() { return major; }
    public String getIntroduce() { return introduce; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
