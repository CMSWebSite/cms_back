package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "qna_posts")
@SQLDelete(sql = "UPDATE qna_posts SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class QnaPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 100)
    private String writerName;

    @Column(length = 200)
    private String writerEmail;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(length = 120)
    private String answeredBy;

    private LocalDateTime answeredAt;

    @Column(nullable = false, columnDefinition = "TINYINT(1) NOT NULL DEFAULT 0")
    private boolean answered = false;

    @Column(nullable = false, columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1")
    private boolean visible = true;

    private LocalDateTime deletedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected QnaPost() {}

    public static QnaPost createFromUser(String title, String content,
                                         String writerName, String writerEmail) {
        QnaPost q = new QnaPost();
        q.title = title;
        q.content = content;
        q.writerName = writerName;
        q.writerEmail = writerEmail;
        q.visible = true;
        q.answered = false;
        q.createdAt = LocalDateTime.now();
        q.updatedAt = q.createdAt;
        return q;
    }

    public void updateByAdmin(String title, String content,
                              boolean visible, String answer, boolean answered, String actor) {
        this.title = title;
        this.content = content;
        this.visible = visible;
        this.answer = answer;
        if (answered != this.answered || (answer != null && !answer.isBlank())) {
            this.answered = answered;
            if (answered) {
                this.answeredAt = LocalDateTime.now();
                this.answeredBy = actor;
            } else {
                this.answeredAt = null;
                this.answeredBy = null;
            }
        }
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriterName() { return writerName; }
    public String getWriterEmail() { return writerEmail; }
    public String getAnswer() { return answer; }
    public String getAnsweredBy() { return answeredBy; }
    public LocalDateTime getAnsweredAt() { return answeredAt; }
    public boolean isAnswered() { return answered; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
