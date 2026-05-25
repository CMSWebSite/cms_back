package cms_back.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gallery_images")
public class GalleryImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "album_id", nullable = false)
    private GalleryAlbum album;

    @Column(nullable = false, length = 500)
    private String imageUrl;

    @Column(length = 300)
    private String caption;

    @Column(nullable = false)
    private int sortOrder = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected GalleryImage() {}

    public static GalleryImage create(String imageUrl, String caption, int sortOrder) {
        GalleryImage img = new GalleryImage();
        img.imageUrl = imageUrl;
        img.caption = caption;
        img.sortOrder = sortOrder;
        img.createdAt = LocalDateTime.now();
        img.updatedAt = img.createdAt;
        return img;
    }

    void attachTo(GalleryAlbum album, int sortOrder) {
        this.album = album;
        this.sortOrder = sortOrder;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public GalleryAlbum getAlbum() { return album; }
    public String getImageUrl() { return imageUrl; }
    public String getCaption() { return caption; }
    public int getSortOrder() { return sortOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
