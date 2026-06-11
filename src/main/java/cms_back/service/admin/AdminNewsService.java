package cms_back.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.News;
import cms_back.dto.admin.NewsDtos;
import cms_back.repository.NewsRepository;
import cms_back.service.exception.NotFoundException;

import java.util.List;

@Service
public class AdminNewsService {

    private final NewsRepository newsRepository;
    private final AdminContext adminContext;

    public AdminNewsService(NewsRepository newsRepository, AdminContext adminContext) {
        this.newsRepository = newsRepository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public List<NewsDtos.Summary> list() {
        return newsRepository.findAllByOrderByPublishedAtDesc().stream()
                .map(NewsDtos.Summary::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public NewsDtos.Detail get(Long id) {
        return NewsDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public NewsDtos.Detail create(NewsDtos.UpsertRequest req) {
        News saved = newsRepository.save(News.create(
                req.title(), req.content(), req.coverImageUrl(),
                req.publishedAt(), req.visible(),
                adminContext.currentActor()
        ));
        return NewsDtos.Detail.from(saved);
    }

    @Transactional
    public NewsDtos.Detail update(Long id, NewsDtos.UpsertRequest req) {
        News n = findOrThrow(id);
        n.update(req.title(), req.content(), req.coverImageUrl(),
                req.publishedAt(), req.visible(),
                adminContext.currentActor());
        return NewsDtos.Detail.from(n);
    }

    @Transactional
    public void delete(Long id) {
        News n = findOrThrow(id);
        // @SQLDelete가 deleted_at을 NOW()로 채워 소프트 삭제 처리한다.
        newsRepository.delete(n);
    }

    private News findOrThrow(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 없습니다."));
    }
}
