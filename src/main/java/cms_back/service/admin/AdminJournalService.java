package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Journal;
import cms_back.dto.admin.JournalDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.JournalRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class AdminJournalService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "publishedDate");

    private final JournalRepository journalRepository;
    private final AdminContext adminContext;

    public AdminJournalService(JournalRepository journalRepository, AdminContext adminContext) {
        this.journalRepository = journalRepository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public PageResponse<JournalDtos.Summary> list(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                journalRepository.search(null, keyword, pageable),
                JournalDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public JournalDtos.Detail get(Long id) {
        return JournalDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public JournalDtos.Detail create(JournalDtos.UpsertRequest req) {
        Journal saved = journalRepository.save(Journal.create(
                req.title1(), req.title2(), req.publishedDate(), req.authors(),
                req.journalName(), req.volume(), req.issue(), req.pages(), req.doi(),
                req.metaLines(), req.highlights(), req.abstractText(),
                req.attachmentName(), req.attachmentUrl(), req.attachmentSize(),
                req.visible(),
                adminContext.currentActor()
        ));
        return JournalDtos.Detail.from(saved);
    }

    @Transactional
    public JournalDtos.Detail update(Long id, JournalDtos.UpsertRequest req) {
        Journal j = findOrThrow(id);
        j.update(req.title1(), req.title2(), req.publishedDate(), req.authors(),
                req.journalName(), req.volume(), req.issue(), req.pages(), req.doi(),
                req.metaLines(), req.highlights(), req.abstractText(),
                req.attachmentName(), req.attachmentUrl(), req.attachmentSize(),
                req.visible(),
                adminContext.currentActor());
        return JournalDtos.Detail.from(j);
    }

    @Transactional
    public void delete(Long id) {
        journalRepository.delete(findOrThrow(id));
    }

    private Journal findOrThrow(Long id) {
        return journalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 논문을 찾을 수 없습니다."));
    }
}
