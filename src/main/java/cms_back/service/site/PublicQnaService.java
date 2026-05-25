package cms_back.service.site;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.QnaPost;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicQnaDtos;
import cms_back.repository.QnaPostRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class PublicQnaService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "createdAt");

    private final QnaPostRepository repository;

    public PublicQnaService(QnaPostRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PageResponse<PublicQnaDtos.Summary> list(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(true, null, keyword, pageable),
                PublicQnaDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public PublicQnaDtos.Detail get(Long id) {
        QnaPost q = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 문의를 찾을 수 없습니다."));
        if (!q.isVisible()) {
            throw new NotFoundException("해당 문의를 찾을 수 없습니다.");
        }
        return PublicQnaDtos.Detail.from(q);
    }

    @Transactional
    public PublicQnaDtos.Detail create(PublicQnaDtos.CreateRequest req) {
        QnaPost saved = repository.save(QnaPost.createFromUser(
                req.title(), req.content(), req.writerName(), req.writerEmail()
        ));
        return PublicQnaDtos.Detail.from(saved);
    }
}
