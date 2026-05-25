package cms_back.controller.admin;

import org.springframework.web.bind.annotation.*;

import cms_back.dto.admin.DashboardDtos;
import cms_back.service.admin.DashboardService;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final DashboardService dashboardService;

    public AdminDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardDtos.Summary summary() {
        return dashboardService.summary();
    }
}
