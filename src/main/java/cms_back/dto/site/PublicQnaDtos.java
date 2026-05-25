package cms_back.dto.site;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import cms_back.domain.QnaPost;

import java.time.LocalDateTime;

public final class PublicQnaDtos {

    private PublicQnaDtos() {}

    public record Summary(
            Long id,
            String title,
            String writerName,
            boolean answered,
            LocalDateTime createdAt
    ) {
        public static Summary from(QnaPost q) {
            return new Summary(q.getId(), q.getTitle(), q.getWriterName(),
                    q.isAnswered(), q.getCreatedAt());
        }
    }

    public record Detail(
            Long id,
            String title,
            String content,
            String writerName,
            String answer,
            boolean answered,
            LocalDateTime answeredAt,
            LocalDateTime createdAt
    ) {
        public static Detail from(QnaPost q) {
            return new Detail(q.getId(), q.getTitle(), q.getContent(),
                    q.getWriterName(), q.getAnswer(), q.isAnswered(),
                    q.getAnsweredAt(), q.getCreatedAt());
        }
    }

    public record CreateRequest(
            @NotBlank(message = "제목은 필수입니다.") @Size(max = 300) String title,
            @NotBlank(message = "내용은 필수입니다.") String content,
            @NotBlank(message = "이름은 필수입니다.") @Size(max = 100) String writerName,
            @Email(message = "이메일 형식이 올바르지 않습니다.") @Size(max = 200) String writerEmail
    ) {}
}
