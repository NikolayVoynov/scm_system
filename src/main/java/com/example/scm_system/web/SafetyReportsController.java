package com.example.scm_system.web;

import com.example.scm_system.model.binding.SafetyReportSendBindingModel;
import com.example.scm_system.model.service.SafetyReportSendServiceModel;
import com.example.scm_system.service.SafetyReportService;
import com.example.scm_system.service.impl.SystemUserSpring;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SafetyReportsController {

    private final SafetyReportService safetyReportService;
    private final ModelMapper modelMapper;

    public SafetyReportsController(SafetyReportService safetyReportService, ModelMapper modelMapper) {
        this.safetyReportService = safetyReportService;
        this.modelMapper = modelMapper;
    }

    // ADD

    @GetMapping("/reports/send")
    public String getSafetyReportSendPage(Model model) {

        if (!model.containsAttribute("safetyReportSendBindingModel")) {
            model.addAttribute("safetyReportSendBindingModel", new SafetyReportSendBindingModel());
        }

        return "report-send";
    }

    @PostMapping("/reports/send")
    public String sendSafetyReport(@Valid SafetyReportSendBindingModel safetyReportSendBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal SystemUserSpring systemUserSpring) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("safetyReportSendBindingModel", safetyReportSendBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.safetyReportSendBindingModel", bindingResult);

            return "/reports/send";
        }

        SafetyReportSendServiceModel safetyReportSendServiceModel = safetyReportService.sendSafetyReport(safetyReportSendBindingModel, systemUserSpring.getUserIdentifier());

        return "redirect:/reports/" + safetyReportSendServiceModel.getId() + "/details";
    }

    // UPDATE

    // DELETE
}
