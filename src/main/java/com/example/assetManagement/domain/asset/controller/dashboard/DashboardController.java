package com.example.assetManagement.domain.asset.controller.dashboard;

import com.example.assetManagement.domain.asset.dto.dashboard.DashboardResponse;
import com.example.assetManagement.domain.asset.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public String goToDashBoard(Model model) {
        DashboardResponse dashboard = dashboardService.getDashboardInfo();
        model.addAttribute("dashboard", dashboard);
        return "dashboard/dashboard";
    }

}
