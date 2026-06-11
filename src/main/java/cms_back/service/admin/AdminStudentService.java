package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Student;
import cms_back.domain.StudentStatus;
import cms_back.dto.admin.StudentDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.StudentRepository;
import cms_back.service.exception.BusinessRuleException;
import cms_back.service.exception.NotFoundException;

@Service
public class AdminStudentService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "enrolledAt");

    private final StudentRepository studentRepository;
    private final AdminContext adminContext;

    public AdminStudentService(StudentRepository studentRepository, AdminContext adminContext) {
        this.studentRepository = studentRepository;
        this.adminContext = adminContext;
    }

    @Transactional(readOnly = true)
    public PageResponse<StudentDtos.Summary> list(int page, int limit, String keyword,
                                                  StudentStatus status, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                studentRepository.search(null, status, keyword, pageable),
                StudentDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public StudentDtos.Detail get(Long id) {
        return StudentDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public StudentDtos.Detail create(StudentDtos.UpsertRequest req) {
        if (studentRepository.existsBySlug(req.slug())) {
            throw new BusinessRuleException("SLUG_DUPLICATE", "이미 사용 중인 슬러그입니다.");
        }
        Student saved = studentRepository.save(Student.create(
                req.slug(), req.koreanName(), req.englishName(),
                req.enrolledAt(), req.role(), req.majors(),
                req.researchInterests(), req.career(), req.publications(),
                req.email(),
                req.photoUrl(), req.status(),
                req.visible(),
                adminContext.currentActor()
        ));
        return StudentDtos.Detail.from(saved);
    }

    @Transactional
    public StudentDtos.Detail update(Long id, StudentDtos.UpsertRequest req) {
        Student s = findOrThrow(id);
        if (!s.getSlug().equals(req.slug()) && studentRepository.existsBySlug(req.slug())) {
            throw new BusinessRuleException("SLUG_DUPLICATE", "이미 사용 중인 슬러그입니다.");
        }
        s.update(req.slug(), req.koreanName(), req.englishName(),
                req.enrolledAt(), req.role(), req.majors(),
                req.researchInterests(), req.career(), req.publications(),
                req.email(),
                req.photoUrl(), req.status(),
                req.visible(),
                adminContext.currentActor());
        return StudentDtos.Detail.from(s);
    }

    @Transactional
    public void delete(Long id) {
        studentRepository.delete(findOrThrow(id));
    }

    private Student findOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 학생을 찾을 수 없습니다."));
    }
}
