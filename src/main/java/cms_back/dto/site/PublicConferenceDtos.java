package cms_back.dto.site;

import cms_back.domain.Conference;

import java.time.LocalDate;

public final class PublicConferenceDtos {

    private PublicConferenceDtos() {}

    public record Summary(
            Long id,
            String title,
            String authors,
            String conferenceName,
            String location,
            LocalDate presentedDate,
            String attachmentUrl
    ) {
        public static Summary from(Conference c) {
            return new Summary(
                    c.getId(), c.getTitle(), c.getAuthors(),
                    c.getConferenceName(), c.getLocation(),
                    c.getPresentedDate(), c.getAttachmentUrl()
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
            Long attachmentSize
    ) {
        public static Detail from(Conference c) {
            return new Detail(
                    c.getId(), c.getTitle(), c.getAuthors(),
                    c.getConferenceName(), c.getLocation(),
                    c.getPresentedDate(), c.getAbstractText(),
                    c.getAttachmentName(), c.getAttachmentUrl(), c.getAttachmentSize()
            );
        }
    }
}
