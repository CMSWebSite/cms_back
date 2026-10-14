package cms_back.service.site;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.ContactApplication;
import cms_back.dto.site.PublicContactDtos;
import cms_back.repository.ContactApplicationRepository;

@Service
public class PublicContactService {

    private final ContactApplicationRepository repository;

    public PublicContactService(ContactApplicationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PublicContactDtos.CreateResponse create(PublicContactDtos.CreateRequest req) {
        ContactApplication saved = repository.save(ContactApplication.createFromUser(
                req.name().trim(),
                req.contact().trim(),
                req.major().trim(),
                req.introduce() == null || req.introduce().isBlank() ? null : req.introduce().trim()
        ));
        return new PublicContactDtos.CreateResponse(saved.getId());
    }
}
