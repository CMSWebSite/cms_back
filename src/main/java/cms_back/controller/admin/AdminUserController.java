package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.UserDtos;
import cms_back.service.admin.AdminUserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService service;

    public AdminUserController(AdminUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDtos.Summary> list() {
        return service.list();
    }

    @PatchMapping("/{id}/role")
    public UserDtos.Summary changeRole(@PathVariable Long id,
                                       @Valid @RequestBody UserDtos.ChangeRoleRequest req) {
        return service.changeRole(id, req.role());
    }
}
