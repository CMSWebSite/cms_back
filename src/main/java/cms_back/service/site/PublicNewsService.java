package cms_back.service.site;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.News;
import cms_back.dto.site.PublicNewsDtos;
import cms_back.repository.NewsRepository;
import cms_back.service.exception.NotFoundException;

import java.util.List;

@Service
public class PublicNewsService {

    private final NewsRepository newsRepository;

    public PublicNewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional(readOnly = true)
    public List<PublicNewsDtos.Summary> list() {
        return newsRepository.findByVisibleTrueOrderByPublishedAtDesc().stream()
                .map(PublicNewsDtos.Summary::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PublicNewsDtos.Detail get(Long id) {
        News n = newsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 없습니다."));
        if (!n.isVisible()) {
            // 숨김 처리된 글은 일반 사용자에게는 존재하지 않는 것처럼 응답한다.
            throw new NotFoundException("해당 게시글을 찾을 수 없습니다.");
        }
        return PublicNewsDtos.Detail.from(n);
    }
}
