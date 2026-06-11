package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.OtherAchievement;
import cms_back.dto.admin.OtherAchievementDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.OtherAchievementRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class AdminOtherAchievementService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "achievedOn");

    private final OtherAchievementRepository repository;
    private final AdminContext adminContext;

    public AdminOtherAchievementService(OtherAchievementRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public PageResponse<OtherAchievementDtos.Summary> list(int page, int limit, String keyword,
                                                            String type, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(null, type, keyword, pageable),
                OtherAchievementDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public OtherAchievementDtos.Detail get(Long id) {
        return OtherAchievementDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public OtherAchievementDtos.Detail create(OtherAchievementDtos.UpsertRequest req) {
        OtherAchievement saved = repository.save(OtherAchievement.create(
                req.title(), req.type(), req.description(),
                req.achievedOn(),
                req.attachmentName(), req.attachmentUrl(), req.attachmentSize(),
                req.visible(),
                adminContext.currentActor()
        ));
        return OtherAchievementDtos.Detail.from(saved);
    }

    @Transactional
    public OtherAchievementDtos.Detail update(Long id, OtherAchievementDtos.UpsertRequest req) {
        OtherAchievement o = findOrThrow(id);
        o.update(req.title(), req.type(), req.description(),
                req.achievedOn(),
                req.attachmentName(), req.attachmentUrl(), req.attachmentSize(),
                req.visible(),
                adminContext.currentActor());
        return OtherAchievementDtos.Detail.from(o);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private OtherAchievement findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 기타 실적을 찾을 수 없습니다."));
    }
}
