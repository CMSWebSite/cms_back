package cms_back.service.site;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Student;
import cms_back.domain.StudentStatus;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.dto.site.PublicStudentDtos;
import cms_back.repository.StudentRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class PublicStudentService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "enrolledAt");

    private final StudentRepository repository;

    public PublicStudentService(StudentRepository repository) {
        this.repository = repository;
    }

    /** /api/public/students — 재학생 목록 (status=ACTIVE & visible). */
    @Transactional(readOnly = true)
    public PageResponse<PublicStudentDtos.Summary> listActive(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(true, StudentStatus.ACTIVE, keyword, pageable),
                PublicStudentDtos.Summary::from
        );
    }

    /** /api/public/alumni — 졸업생 목록 (status=ALUMNI & visible). */
    @Transactional(readOnly = true)
    public PageResponse<PublicStudentDtos.Summary> listAlumni(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(true, StudentStatus.ALUMNI, keyword, pageable),
                PublicStudentDtos.Summary::from
        );
    }

    /** /api/public/students/{slug} — slug 기반 단건 조회. INACTIVE/비공개는 404. */
    @Transactional(readOnly = true)
    public PublicStudentDtos.Detail getBySlug(String slug) {
        Student s = repository.findBySlugAndVisibleTrue(slug)
                .orElseThrow(() -> new NotFoundException("해당 학생을 찾을 수 없습니다."));
        if (s.getStatus() == StudentStatus.INACTIVE) {
            throw new NotFoundException("해당 학생을 찾을 수 없습니다.");
        }
        return PublicStudentDtos.Detail.from(s);
    }
}
