package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import cms_back.domain.Facility;

import java.time.LocalDateTime;

public final class FacilityDtos {

    private FacilityDtos() {}

    public record Summary(
            Long id,
            String name,
            String category,
            String image,
            int quantity,
            int sortOrder,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static Summary from(Facility f) {
            return new Summary(f.getId(), f.getName(), f.getCategory(),
                    f.getImage(), f.getQuantity(), f.getSortOrder(),
                    f.isVisible(), f.getUpdatedAt());
        }
    }

    public record Detail(
            Long id,
            String name,
            String category,
            String description,
            String specification,
            String image,
            int quantity,
            int sortOrder,
            boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy
    ) {
        public static Detail from(Facility f) {
            return new Detail(f.getId(), f.getName(), f.getCategory(),
                    f.getDescription(), f.getSpecification(), f.getImage(),
                    f.getQuantity(), f.getSortOrder(), f.isVisible(),
                    f.getCreatedAt(), f.getUpdatedAt(), f.getCreatedBy(), f.getUpdatedBy());
        }
    }

    public record UpsertRequest(
            @NotBlank(message = "장비명은 필수입니다.") @Size(max = 200) String name,
            @Size(max = 100) String category,
            String description,
            String specification,
            @Size(max = 500) String image,
            @PositiveOrZero int quantity,
            @PositiveOrZero int sortOrder,
            boolean visible
    ) {}
}
