package cms_back.service.site;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.GalleryAlbum;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicGalleryDtos;
import cms_back.repository.GalleryAlbumRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class PublicGalleryService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "eventDate").and(Sort.by(Sort.Direction.DESC, "id"));

    private final GalleryAlbumRepository repository;

    public PublicGalleryService(GalleryAlbumRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PageResponse<PublicGalleryDtos.AlbumSummary> list(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(true, keyword, pageable),
                PublicGalleryDtos.AlbumSummary::from
        );
    }

    @Transactional(readOnly = true)
    public PublicGalleryDtos.AlbumDetail get(Long id) {
        GalleryAlbum a = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 앨범을 찾을 수 없습니다."));
        if (!a.isVisible()) {
            throw new NotFoundException("해당 앨범을 찾을 수 없습니다.");
        }
        return PublicGalleryDtos.AlbumDetail.from(a);
    }
}
