package cms_back.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.VisionMissionDtos;
import cms_back.service.admin.AdminVisionMissionService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/vision-mission")
public class AdminVisionMissionController {

    private final AdminVisionMissionService service;

    public AdminVisionMissionController(AdminVisionMissionService service) {
        this.service = service;
    }

    @GetMapping
    public List<VisionMissionDtos.Section> list() { return service.list(); }

    @GetMapping("/{id}")
    public VisionMissionDtos.Section get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisionMissionDtos.Section create(@Valid @RequestBody VisionMissionDtos.UpsertRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public VisionMissionDtos.Section update(@PathVariable Long id,
                                            @Valid @RequestBody VisionMissionDtos.UpsertRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
