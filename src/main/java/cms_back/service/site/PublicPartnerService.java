package cms_back.service.site;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.dto.site.PublicPartnerDtos;
import cms_back.repository.PartnerRepository;

import java.util.List;

@Service
public class PublicPartnerService {

    private final PartnerRepository repository;

    public PublicPartnerService(PartnerRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PublicPartnerDtos.Item> list() {
        return repository.findByVisibleTrueOrderBySortOrderAscIdAsc().stream()
                .map(PublicPartnerDtos.Item::from)
                .toList();
    }
}
