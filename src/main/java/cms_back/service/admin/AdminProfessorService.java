package cms_back.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.Professor;
import cms_back.dto.admin.ProfessorDtos;
import cms_back.repository.ProfessorRepository;

@Service
public class AdminProfessorService {

    private final ProfessorRepository repository;
    private final AdminContext adminContext;

    public AdminProfessorService(ProfessorRepository repository, AdminContext adminContext) {
        this.repository = repository;
        this.adminContext = adminContext;
    }

    @Transactional
    public ProfessorDtos.Detail get() {
        Professor p = repository.findFirstByOrderByIdAsc()
                .orElseGet(() -> repository.save(Professor.createDefault(adminContext.currentActor())));
        return ProfessorDtos.Detail.from(p);
    }

    @Transactional
    public ProfessorDtos.Detail update(ProfessorDtos.UpsertRequest req) {
        Professor p = repository.findFirstByOrderByIdAsc()
                .orElseGet(() -> repository.save(Professor.createDefault(adminContext.currentActor())));
        p.update(
                req.name(), req.position(), req.profileImage(),
                req.email(), req.phone(), req.office(), req.biography(),
                req.researchFields(), req.education(), req.career(), req.publications(),
                req.visible(),
                adminContext.currentActor()
        );
        return ProfessorDtos.Detail.from(p);
    }
}
