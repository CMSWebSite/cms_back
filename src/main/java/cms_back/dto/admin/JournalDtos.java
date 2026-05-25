package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import cms_back.domain.Journal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class JournalDtos {

    private JournalDtos() {}

    public record Summary(
            Long id,
            String title1,
            String title2,
            LocalDate publishedDate,
            String authors,
            String journalName,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Summary from(Journal j) {
            return new Summary(
                    j.getId(), j.getTitle1(), j.getTitle2(),
                    j.getPublishedDate(), j.getAuthors(), j.getJournalName(),
                    j.isVisible(), j.getUpdatedAt()
            );
        }
    }

    public record Detail(
            Long id,
            String title1,
            String title2,
            LocalDate publishedDate,
            String authors,
            String journalName,
            String volume,
            String issue,
            String pages,
            String doi,
            List<String> metaLines,
            List<String> highlights,
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
        public static Detail from(Journal j) {
            return new Detail(
                    j.getId(), j.getTitle1(), j.getTitle2(),
                    j.getPublishedDate(), j.getAuthors(),
                    j.getJournalName(), j.getVolume(), j.getIssue(), j.getPages(), j.getDoi(),
                    j.getMetaLines(), j.getHighlights(), j.getAbstractText(),
                    j.getAttachmentName(), j.getAttachmentUrl(), j.getAttachmentSize(),
                    j.isVisible(),
                    j.getCreatedAt(), j.getUpdatedAt(), j.getCreatedBy(), j.getUpdatedBy()
            );
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "제목(1행)은 필수입니다.")
            @Size(max = 300)
            String title1,

            @Size(max = 300)
            String title2,

            @NotNull(message = "게재일은 필수입니다.")
            LocalDate publishedDate,

            @NotBlank(message = "저자는 필수입니다.")
            @Size(max = 1000)
            String authors,

            @Size(max = 300)
            String journalName,

            @Size(max = 50)
            String volume,

            @Size(max = 50)
            String issue,

            @Size(max = 50)
            String pages,

            @Size(max = 200)
            String doi,

            List<String> metaLines,
            List<String> highlights,
            String abstractText,

            @Size(max = 300)
            String attachmentName,

            @Size(max = 500)
            String attachmentUrl,

            Long attachmentSize,

            boolean visible
    ) {}
}
