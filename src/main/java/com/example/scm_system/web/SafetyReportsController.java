package com.example.scm_system.web;

import com.example.scm_system.model.binding.SafetyReportSendBindingModel;
import com.example.scm_system.model.binding.SafetyReportUpdateBindingModel;
import com.example.scm_system.model.entity.enums.StatusSafetyReportEnum;
import com.example.scm_system.model.service.SafetyReportSendServiceModel;
import com.example.scm_system.model.service.SafetyReportUpdateServiceModel;
import com.example.scm_system.model.view.SafetyReportDetailsViewModel;
import com.example.scm_system.service.SafetyReportService;
import com.example.scm_system.service.impl.SystemUserSpring;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

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
                                   @AuthenticationPrincipal SystemUserSpring systemUserSpring) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("safetyReportSendBindingModel", safetyReportSendBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.safetyReportSendBindingModel", bindingResult);

            return "redirect:/reports/send";
        }

        SafetyReportSendServiceModel safetyReportSendServiceModel = safetyReportService.sendSafetyReport(safetyReportSendBindingModel, systemUserSpring.getUserIdentifier());

        return "redirect:/reports/" + safetyReportSendServiceModel.getId() + "/details";
    }

    // DETAILS

    @GetMapping("/reports/{id}/details")
    public String showSafetyReport(@PathVariable Long id, Model model, Principal principal) {

        model.addAttribute("safetyReport", safetyReportService.findById(id, principal.getName()));

        return "report-details";
    }

    // UPDATE

    @GetMapping("/reports/{id}/update")
    public String updateSafetyReport(@PathVariable Long id, Model model, @AuthenticationPrincipal SystemUserSpring systemUserSpring) {

        SafetyReportDetailsViewModel safetyReportDetailsViewModel = safetyReportService.findById(id, systemUserSpring.getUserIdentifier());
        SafetyReportUpdateBindingModel safetyReportUpdateBindingModel = modelMapper.map(safetyReportDetailsViewModel, SafetyReportUpdateBindingModel.class);

        model.addAttribute("safetyReportUpdateBindingModel", safetyReportUpdateBindingModel);
        model.addAttribute("status", StatusSafetyReportEnum.values());

        return "report-update";
    }

    @GetMapping("/reports/{id}/update/errors")
    public String updateSafetyReportErrors(@PathVariable Long id, Model model) {

        model.addAttribute("status", StatusSafetyReportEnum.values());

        return "report-update";
    }

    @PatchMapping("/reports/{id}/update")
    public String updateSafetyReport(@PathVariable Long id,
                                     @Valid SafetyReportUpdateBindingModel safetyReportUpdateBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("safetyReportUpdateBindingModel", safetyReportUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.safetyReportUpdateBindingModel", bindingResult);

            return "redirect:/reports/" + id + "/update/errors";
        }

        SafetyReportUpdateServiceModel safetyReportUpdateServiceModel =
                modelMapper.map(safetyReportUpdateBindingModel, SafetyReportUpdateServiceModel.class);

        safetyReportUpdateServiceModel.setId(id);

        safetyReportService.updateSafetyReport(safetyReportUpdateServiceModel);

        return "redirect:/reports/" + id + "/details";

    }

    // DELETE

    @PreAuthorize("isOwner(#id)")
    @DeleteMapping("/reports/{id}")
    public String deleteAudit(@PathVariable Long id,
                              Principal principal) {

        safetyReportService.deleteSafetyReport(id);

        return "redirect:/users/home";
    }
}
