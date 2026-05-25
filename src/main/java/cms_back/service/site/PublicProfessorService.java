package cms_back.service.site;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.dto.site.PublicProfessorDtos;
import cms_back.repository.ProfessorRepository;
import cms_back.service.exception.NotFoundException;

@Service
public class PublicProfessorService {

    private final ProfessorRepository repository;

    public PublicProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public PublicProfessorDtos.Detail get() {
        return repository.findFirstByVisibleTrueOrderByIdAsc()
                .map(PublicProfessorDtos.Detail::from)
                .orElseThrow(() -> new NotFoundException("교수 정보가 등록되지 않았습니다."));
    }
}
