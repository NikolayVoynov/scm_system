package com.example.scm_system.web;

import com.example.scm_system.model.entity.SafetyReportEntity;
import com.example.scm_system.model.view.AuditDetailsViewModel;
import com.example.scm_system.model.view.NonconformityDetailsViewModel;
import com.example.scm_system.model.view.SafetyReportDetailsViewModel;
import com.example.scm_system.service.AuditService;
import com.example.scm_system.service.NonconformityService;
import com.example.scm_system.service.SafetyReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {

    private final AuditService auditService;
    private final SafetyReportService safetyReportService;
    private final NonconformityService nonconformityService;

    public DashboardController(AuditService auditService, SafetyReportService safetyReportService, NonconformityService nonconformityService) {
        this.auditService = auditService;
        this.safetyReportService = safetyReportService;
        this.nonconformityService = nonconformityService;
    }

    @GetMapping("/dashboard")
    String getPersonalDashboard(Model model, Principal principal) {

        List<SafetyReportDetailsViewModel> listSafetyReports = safetyReportService.findSafetyReports(principal);
        List<AuditDetailsViewModel> listAudits = auditService.findAudits(principal);
        List<NonconformityDetailsViewModel> listNonconformities = nonconformityService.findNonconformities(principal);

        model.addAttribute("safetyReports", listSafetyReports);
        model.addAttribute("audits", listAudits);
        model.addAttribute("nonconformities", listNonconformities);

        return "dashboard";
    }
}
