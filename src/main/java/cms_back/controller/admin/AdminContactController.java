package cms_back.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.ContactApplicationDtos;
import cms_back.dto.common.PageResponse;
import cms_back.service.admin.AdminContactService;

@RestController
@RequestMapping("/api/admin/contact-applications")
public class AdminContactController {

    private final AdminContactService service;

    public AdminContactController(AdminContactService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<ContactApplicationDtos.Summary> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort
    ) {
        return service.list(page, limit, keyword, sort);
    }

    @GetMapping("/{id}")
    public ContactApplicationDtos.Detail get(@PathVariable Long id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
