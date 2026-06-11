package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.NewsDtos;
import cms_back.service.admin.AdminNewsService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/news")
public class AdminNewsController {

    private final AdminNewsService service;

    public AdminNewsController(AdminNewsService service) {
        this.service = service;
    }

    @GetMapping
    public List<NewsDtos.Summary> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public NewsDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDtos.Detail create(@Valid @RequestBody NewsDtos.UpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public NewsDtos.Detail update(@PathVariable Long id,
                                  @Valid @RequestBody NewsDtos.UpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
