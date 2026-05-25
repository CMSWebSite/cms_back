package cms_back.dto.site;

import cms_back.domain.Journal;

import java.time.LocalDate;
import java.util.List;

/**
 * 일반 사용자에게 노출되는 Journal DTO. 관리자 메타(createdBy/updatedBy/updatedAt 등)는 제외한다.
 */
public final class PublicJournalDtos {

    private PublicJournalDtos() {}

    public record Summary(
            Long id,
            String title1,
            String title2,
            LocalDate publishedDate,
            String authors,
            String journalName,
            String attachmentUrl
    ) {
        public static Summary from(Journal j) {
            return new Summary(
                    j.getId(), j.getTitle1(), j.getTitle2(),
                    j.getPublishedDate(), j.getAuthors(), j.getJournalName(),
                    j.getAttachmentUrl()
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
            Long attachmentSize
    ) {
        public static Detail from(Journal j) {
            return new Detail(
                    j.getId(), j.getTitle1(), j.getTitle2(),
                    j.getPublishedDate(), j.getAuthors(),
                    j.getJournalName(), j.getVolume(), j.getIssue(), j.getPages(), j.getDoi(),
                    j.getMetaLines(), j.getHighlights(), j.getAbstractText(),
                    j.getAttachmentName(), j.getAttachmentUrl(), j.getAttachmentSize()
            );
        }
    }
}
