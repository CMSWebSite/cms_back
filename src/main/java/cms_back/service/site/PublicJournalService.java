package cms_back.service.site;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Journal;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicJournalDtos;
import cms_back.repository.JournalRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class PublicJournalService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "publishedDate");

    private final JournalRepository journalRepository;

    public PublicJournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @Transactional(readOnly = true)
    public PageResponse<PublicJournalDtos.Summary> list(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                journalRepository.search(true, keyword, pageable),
                PublicJournalDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public PublicJournalDtos.Detail get(Long id) {
        Journal j = journalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 논문을 찾을 수 없습니다."));
        if (!j.isVisible()) {
            throw new NotFoundException("해당 논문을 찾을 수 없습니다.");
        }
        return PublicJournalDtos.Detail.from(j);
    }
}
