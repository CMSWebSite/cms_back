package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import cms_back.domain.QnaPost;

import java.time.LocalDateTime;

public final class QnaDtos {

    private QnaDtos() {}

    public record Summary(
            Long id,
            String title,
            String writerName,
            boolean answered,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Summary from(QnaPost q) {
            return new Summary(q.getId(), q.getTitle(), q.getWriterName(),
                    q.isAnswered(), q.isVisible(),
                    q.getCreatedAt(), q.getUpdatedAt());
        }
    }

    public record Detail(
            Long id,
            String title,
            String content,
            String writerName,
            String writerEmail,
            String answer,
            String answeredBy,
            LocalDateTime answeredAt,
            boolean answered,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Detail from(QnaPost q) {
            return new Detail(q.getId(), q.getTitle(), q.getContent(),
                    q.getWriterName(), q.getWriterEmail(),
                    q.getAnswer(), q.getAnsweredBy(), q.getAnsweredAt(),
                    q.isAnswered(), q.isVisible(),
                    q.getCreatedAt(), q.getUpdatedAt());
        }
    }

    public record AdminUpdateRequest(
            @NotBlank @Size(max = 300) String title,
            @NotBlank String content,
            String answer,
            boolean answered,
            boolean visible
    ) {}
}
