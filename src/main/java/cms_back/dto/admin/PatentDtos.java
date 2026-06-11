package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import cms_back.domain.Patent;
import cms_back.domain.PatentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class PatentDtos {

    private PatentDtos() {}

    public record Summary(
            Long id,
            String title,
            String inventors,
            String applicationNumber,
            String registrationNumber,
            LocalDate applicationDate,
            LocalDate registrationDate,
            PatentStatus status,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Summary from(Patent p) {
            return new Summary(
                    p.getId(), p.getTitle(), p.getInventors(),
                    p.getApplicationNumber(), p.getRegistrationNumber(),
                    p.getApplicationDate(), p.getRegistrationDate(),
                    p.getStatus(), p.isVisible(), p.getUpdatedAt()
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
            String abstractText,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy
    ) {
        public static Detail from(Patent p) {
            return new Detail(
                    p.getId(), p.getTitle(), p.getInventors(),
                    p.getApplicationNumber(), p.getRegistrationNumber(),
                    p.getApplicationDate(), p.getRegistrationDate(),
                    p.getStatus(), p.getAbstractText(),
                    p.isVisible(),
                    p.getCreatedAt(), p.getUpdatedAt(), p.getCreatedBy(), p.getUpdatedBy()
            );
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "제목은 필수입니다.")
            @Size(max = 500)
            String title,

            @NotBlank(message = "발명자는 필수입니다.")
            @Size(max = 1000)
            String inventors,

            @Size(max = 100) String applicationNumber,
            @Size(max = 100) String registrationNumber,
            LocalDate applicationDate,
            LocalDate registrationDate,

            @NotNull(message = "상태는 필수입니다.")
            PatentStatus status,

            String abstractText,

            boolean visible
    ) {}
}
