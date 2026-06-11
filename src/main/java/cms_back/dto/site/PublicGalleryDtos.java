package cms_back.dto.site;

import cms_back.domain.GalleryAlbum;
import cms_back.domain.GalleryImage;

import java.time.LocalDate;
import java.util.List;

public final class PublicGalleryDtos {

    private PublicGalleryDtos() {}

    public record AlbumSummary(
            Long id,
            String title,
            String thumbnailImage,
            LocalDate eventDate,
            int imageCount
    ) {
        public static AlbumSummary from(GalleryAlbum a) {
            return new AlbumSummary(a.getId(), a.getTitle(), a.getThumbnailImage(),
                    a.getEventDate(), a.getImages().size());
        }
    }

    public record Image(Long id, String imageUrl, String caption, int sortOrder) {
        public static Image from(GalleryImage img) {
            return new Image(img.getId(), img.getImageUrl(), img.getCaption(), img.getSortOrder());
        }
    }

    public record AlbumDetail(
            Long id,
            String title,
            String description,
            String thumbnailImage,
            LocalDate eventDate,
            List<Image> images
    ) {
        public static AlbumDetail from(GalleryAlbum a) {
            return new AlbumDetail(a.getId(), a.getTitle(), a.getDescription(),
                    a.getThumbnailImage(), a.getEventDate(),
                    a.getImages().stream().map(Image::from).toList());
        }
    }
}
