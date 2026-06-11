package cms_back.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import cms_back.domain.GalleryAlbum;
import cms_back.domain.GalleryImage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class GalleryDtos {

    private GalleryDtos() {}

    public record AlbumSummary(
            Long id,
            String title,
            String thumbnailImage,
            LocalDate eventDate,
            int imageCount,
            boolean visible,
            LocalDateTime updatedAt
    ) {
        public static AlbumSummary from(GalleryAlbum a) {
            return new AlbumSummary(a.getId(), a.getTitle(), a.getThumbnailImage(),
                    a.getEventDate(), a.getImages().size(),
                    a.isVisible(), a.getUpdatedAt());
        }
    }

    public record ImagePayload(
            @NotBlank @Size(max = 500) String imageUrl,
            @Size(max = 300) String caption
    ) {}

    public record AlbumDetail(
            Long id,
            String title,
            String description,
            String thumbnailImage,
            LocalDate eventDate,
            boolean visible,
            List<ImageItem> images,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String updatedBy
    ) {
        public static AlbumDetail from(GalleryAlbum a) {
            return new AlbumDetail(a.getId(), a.getTitle(), a.getDescription(),
                    a.getThumbnailImage(), a.getEventDate(), a.isVisible(),
                    a.getImages().stream().map(ImageItem::from).toList(),
                    a.getCreatedAt(), a.getUpdatedAt(), a.getCreatedBy(), a.getUpdatedBy());
        }
    }

    public record ImageItem(Long id, String imageUrl, String caption, int sortOrder) {
        public static ImageItem from(GalleryImage img) {
            return new ImageItem(img.getId(), img.getImageUrl(), img.getCaption(), img.getSortOrder());
        }
    }

    public record AlbumUpsertRequest(
            @NotBlank(message = "앨범 제목은 필수입니다.") @Size(max = 300) String title,
            String description,
            @Size(max = 500) String thumbnailImage,
            @NotNull LocalDate eventDate,
            boolean visible,
            /** 입력 순서가 그대로 sortOrder가 된다. */
            List<ImagePayload> images
    ) {}
}
