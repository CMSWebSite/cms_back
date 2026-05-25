package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.PartnerDtos;
import cms_back.service.admin.AdminPartnerService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/partners")
public class AdminPartnerController {

    private final AdminPartnerService service;

    public AdminPartnerController(AdminPartnerService service) {
        this.service = service;
    }

    @GetMapping
    public List<PartnerDtos.Item> list() { return service.list(); }

    @GetMapping("/{id}")
    public PartnerDtos.Item get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartnerDtos.Item create(@Valid @RequestBody PartnerDtos.UpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public PartnerDtos.Item update(@PathVariable Long id,
                                   @Valid @RequestBody PartnerDtos.UpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
