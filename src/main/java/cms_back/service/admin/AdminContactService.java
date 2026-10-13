package cms_back.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.ContactApplication;
import cms_back.dto.admin.ContactApplicationDtos;
import cms_back.dto.common.ListQueryParams;
import cms_back.dto.common.PageResponse;
import cms_back.repository.ContactApplicationRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class AdminContactService {

    private static final Sort DEFAULT_SORT =
            Sort.by(Sort.Direction.DESC, "createdAt");

    private final ContactApplicationRepository repository;

    public AdminContactService(ContactApplicationRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PageResponse<ContactApplicationDtos.Summary> list(int page, int limit, String keyword, String sort) {
        Pageable pageable = ListQueryParams.toPageable(page, limit, sort, DEFAULT_SORT);
        return PageResponse.from(
                repository.search(keyword, pageable),
                ContactApplicationDtos.Summary::from
        );
    }

    @Transactional(readOnly = true)
    public ContactApplicationDtos.Detail get(Long id) {
        return ContactApplicationDtos.Detail.from(findOrThrow(id));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private ContactApplication findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 지원 정보를 찾을 수 없습니다."));
    }
}
