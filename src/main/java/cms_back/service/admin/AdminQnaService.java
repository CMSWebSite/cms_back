package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.QnaPost;
import cms_back.dto.admin.QnaDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.QnaPostRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class AdminQnaService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "createdAt");

    private final QnaPostRepository repository;
    private final AdminContext adminContext;

    public AdminQnaService(QnaPostRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public PageResponse<QnaDtos.Summary> list(int page, int limit, String keyword,
                                              Boolean answered, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(null, answered, keyword, pageable),
                QnaDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public QnaDtos.Detail get(Long id) {
        return QnaDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public QnaDtos.Detail update(Long id, QnaDtos.AdminUpdateRequest req) {
        QnaPost q = findOrThrow(id);
        q.updateByAdmin(req.title(), req.content(), req.visible(),
                req.answer(), req.answered(), adminContext.currentActor());
        return QnaDtos.Detail.from(q);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private QnaPost findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 문의를 찾을 수 없습니다."));
    }
}
