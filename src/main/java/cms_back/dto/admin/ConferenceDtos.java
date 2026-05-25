package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import cms_back.domain.Conference;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ConferenceDtos {

    private ConferenceDtos() {}

    public record Summary(
            Long id,
            String title,
            String authors,
            String conferenceName,
            String location,
            LocalDate presentedDate,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Summary from(Conference c) {
            return new Summary(
                    c.getId(), c.getTitle(), c.getAuthors(),
                    c.getConferenceName(), c.getLocation(),
                    c.getPresentedDate(), c.isVisible(), c.getUpdatedAt()
            );
        }
    }

    public record Detail(
            Long id,
            String title,
            String authors,
            String conferenceName,
            String location,
            LocalDate presentedDate,
            String abstractText,
            String attachmentName,
            String attachmentUrl,
            Long attachmentSize,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy
    ) {
        public static Detail from(Conference c) {
            return new Detail(
                    c.getId(), c.getTitle(), c.getAuthors(),
                    c.getConferenceName(), c.getLocation(),
                    c.getPresentedDate(), c.getAbstractText(),
                    c.getAttachmentName(), c.getAttachmentUrl(), c.getAttachmentSize(),
                    c.isVisible(),
                    c.getCreatedAt(), c.getUpdatedAt(), c.getCreatedBy(), c.getUpdatedBy()
            );
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "제목은 필수입니다.")
            @Size(max = 300)
            String title,

            @NotBlank(message = "저자는 필수입니다.")
            @Size(max = 1000)
            String authors,

            @NotBlank(message = "학회명은 필수입니다.")
            @Size(max = 300)
            String conferenceName,

            @Size(max = 200)
            String location,

            @NotNull(message = "발표일은 필수입니다.")
            LocalDate presentedDate,

            String abstractText,

            @Size(max = 300) String attachmentName,
            @Size(max = 500) String attachmentUrl,
            Long attachmentSize,

            boolean visible
    ) {}
}
