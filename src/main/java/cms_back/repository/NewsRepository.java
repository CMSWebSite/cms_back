package cms_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cms_back.domain.News;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByOrderByPublishedAtDesc();

    /** 공개 사이트용 — 노출 설정된 게시글만, 게시일 내림차순. */
    List<News> findByVisibleTrueOrderByPublishedAtDesc();

    long count();
}
