package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.GalleryAlbum;
import cms_back.domain.GalleryImage;
import cms_back.dto.admin.GalleryDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.GalleryAlbumRepository;
import cms_back.service.exception.NotFoundException;

import java.util.List;

@Service
public class AdminGalleryService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "eventDate").and(Sort.by(Sort.Direction.DESC, "id"));

    private final GalleryAlbumRepository repository;
    private final AdminContext adminContext;

    public AdminGalleryService(GalleryAlbumRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public PageResponse<GalleryDtos.AlbumSummary> list(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(null, keyword, pageable),
                GalleryDtos.AlbumSummary::from
        );
    }

    @Transactional(readOnly = true)
    public GalleryDtos.AlbumDetail get(Long id) {
        return GalleryDtos.AlbumDetail.from(findOrThrow(id));
    }

    @Transactional
    public GalleryDtos.AlbumDetail create(GalleryDtos.AlbumUpsertRequest req) {
        GalleryAlbum album = GalleryAlbum.create(
                req.title(), req.description(), req.thumbnailImage(),
                req.eventDate(), req.visible(), adminContext.currentActor()
        );
        album.replaceImages(toEntities(req.images()));
        GalleryAlbum saved = repository.save(album);
        return GalleryDtos.AlbumDetail.from(saved);
    }

    @Transactional
    public GalleryDtos.AlbumDetail update(Long id, GalleryDtos.AlbumUpsertRequest req) {
        GalleryAlbum a = findOrThrow(id);
        a.updateMeta(req.title(), req.description(), req.thumbnailImage(),
                req.eventDate(), req.visible(), adminContext.currentActor());
        a.replaceImages(toEntities(req.images()));
        return GalleryDtos.AlbumDetail.from(a);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private List<GalleryImage> toEntities(List<GalleryDtos.ImagePayload> payloads) {
        if (payloads == null) return List.of();
        return payloads.stream()
                .map(p -> GalleryImage.create(p.imageUrl(), p.caption(), 0))
                .toList();
    }

    private GalleryAlbum findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 앨범을 찾을 수 없습니다."));
    }
}
