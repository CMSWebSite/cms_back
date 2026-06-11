package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gallery_albums")
@SQLDelete(sql = "UPDATE gallery_albums SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class GalleryAlbum {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500)
    private String thumbnailImage;

    private LocalDate eventDate;

    @Column(nullable = false, columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1")
    private boolean visible = true;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC, id ASC")
    private List<GalleryImage> images = new ArrayList<>();

    private LocalDateTime deletedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 120) private String createdBy;
    @Column(length = 120) private String updatedBy;

    protected GalleryAlbum() {}

    public static GalleryAlbum create(String title, String description, String thumbnailImage,
                                      LocalDate eventDate, boolean visible, String actor) {
        GalleryAlbum a = new GalleryAlbum();
        a.title = title;
        a.description = description;
        a.thumbnailImage = thumbnailImage;
        a.eventDate = eventDate;
        a.visible = visible;
        a.createdAt = LocalDateTime.now();
        a.updatedAt = a.createdAt;
        a.createdBy = actor;
        a.updatedBy = actor;
        return a;
    }

    public void updateMeta(String title, String description, String thumbnailImage,
                           LocalDate eventDate, boolean visible, String actor) {
        this.title = title;
        this.description = description;
        this.thumbnailImage = thumbnailImage;
        this.eventDate = eventDate;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    /** 전체 이미지 리스트를 입력 순서대로 교체한다. orphanRemoval로 기존 이미지 자동 삭제. */
    public void replaceImages(List<GalleryImage> nextImages) {
        this.images.clear();
        if (nextImages != null) {
            for (int i = 0; i < nextImages.size(); i++) {
                GalleryImage img = nextImages.get(i);
                img.attachTo(this, i);
                this.images.add(img);
            }
        }
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getThumbnailImage() { return thumbnailImage; }
    public LocalDate getEventDate() { return eventDate; }
    public boolean isVisible() { return visible; }
    public List<GalleryImage> getImages() { return images; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
