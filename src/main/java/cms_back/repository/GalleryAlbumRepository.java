package cms_back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cms_back.domain.GalleryAlbum;

public interface GalleryAlbumRepository extends JpaRepository<GalleryAlbum, Long> {

    @Query("""
        SELECT a FROM GalleryAlbum a
        WHERE (:visible IS NULL OR a.visible = :visible)
          AND (
            :keyword IS NULL OR :keyword = ''
            OR LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(COALESCE(a.description, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<GalleryAlbum> search(@Param("visible") Boolean visible,
                              @Param("keyword") String keyword,
                              Pageable pageable);
}
