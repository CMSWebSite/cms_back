package cms_back.dto.site;

import cms_back.domain.Patent;
import cms_back.domain.PatentStatus;

import java.time.LocalDate;

public final class PublicPatentDtos {

    private PublicPatentDtos() {}

    public record Summary(
            Long id,
            String title,
            String inventors,
            String applicationNumber,
            String registrationNumber,
            LocalDate applicationDate,
            LocalDate registrationDate,
            PatentStatus status
    ) {
        public static Summary from(Patent p) {
            return new Summary(
                    p.getId(), p.getTitle(), p.getInventors(),
                    p.getApplicationNumber(), p.getRegistrationNumber(),
                    p.getApplicationDate(), p.getRegistrationDate(),
                    p.getStatus()
            );
        }
    }

    public record Detail(
            Long id,
            String title,
            String inventors,
            String applicationNumber,
            String registrationNumber,
            LocalDate applicationDate,
            LocalDate registrationDate,
            PatentStatus status,
            String abstractText
    ) {
        public static Detail from(Patent p) {
            return new Detail(
                    p.getId(), p.getTitle(), p.getInventors(),
                    p.getApplicationNumber(), p.getRegistrationNumber(),
                    p.getApplicationDate(), p.getRegistrationDate(),
                    p.getStatus(), p.getAbstractText()
            );
        }
    }
}
