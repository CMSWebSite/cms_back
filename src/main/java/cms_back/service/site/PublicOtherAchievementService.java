package cms_back.service.site;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.OtherAchievement;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicOtherAchievementDtos;
import cms_back.repository.OtherAchievementRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class PublicOtherAchievementService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "achievedOn");

    private final OtherAchievementRepository repository;

    public PublicOtherAchievementService(OtherAchievementRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PageResponse<PublicOtherAchievementDtos.Summary> list(int page, int limit, String keyword,
                                                                  String type, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(true, type, keyword, pageable),
                PublicOtherAchievementDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public PublicOtherAchievementDtos.Detail get(Long id) {
        OtherAchievement o = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 항목을 찾을 수 없습니다."));
        if (!o.isVisible()) {
            throw new NotFoundException("해당 항목을 찾을 수 없습니다.");
        }
        return PublicOtherAchievementDtos.Detail.from(o);
    }
}
