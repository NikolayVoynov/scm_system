package com.example.scm_system.web;

import com.example.scm_system.model.binding.AuditAddBindingModel;
import com.example.scm_system.model.binding.AuditUpdateBindingModel;
import com.example.scm_system.model.entity.enums.DepartmentEnum;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;
import com.example.scm_system.model.service.AuditAddServiceModel;
import com.example.scm_system.model.service.AuditUpdateServiceModel;
import com.example.scm_system.model.view.AuditDetailsView;
import com.example.scm_system.service.AuditService;
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
import java.security.Principal;

@Controller
public class AuditsController {

    private final AuditService auditService;
    private final ModelMapper modelMapper;

    public AuditsController(AuditService auditService, ModelMapper modelMapper) {
        this.auditService = auditService;
        this.modelMapper = modelMapper;
    }

    // ADD

    @GetMapping("/audits/add")
    public String getAuditAddPage(Model model) {

        if (!model.containsAttribute("auditAddBindingModel")) {
            model.addAttribute("auditAddBindingModel", new AuditAddBindingModel());
        }

        return "audit-add";
    }

    @PostMapping("/audits/add")
    public String addAudit(@Valid AuditAddBindingModel auditAddBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal SystemUserSpring systemUserSpring) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("auditAddBindingModel", auditAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.auditAddBindingModel", bindingResult);

            return "redirect:/audits/add";
        }

        AuditAddServiceModel auditAddServiceModel = auditService.addAudit(auditAddBindingModel, systemUserSpring.getUserIdentifier());

        return "redirect:/audits/" + auditAddServiceModel.getId() + "/datails";
    }

    // DETAILS

    @GetMapping("/audits/{id}/details")
    public String showAuditReport(@PathVariable Long id, Model model, Principal principal) {

        model.addAttribute("audit", auditService.findById(id, principal.getName()));

        return "audit-details";
    }

    // UPDATE

    @GetMapping("/audits/{id}/update")
    public String updateAudit(@PathVariable Long id, Model model, @AuthenticationPrincipal SystemUserSpring systemUserSpring) {

        AuditDetailsView auditDetailsView = auditService.findById(id, systemUserSpring.getUserIdentifier());
        AuditUpdateBindingModel auditUpdateBindingModel = modelMapper.map(auditDetailsView, AuditUpdateBindingModel.class);

        model.addAttribute("departments", DepartmentEnum.values());
        model.addAttribute("status", StatusAuditEnum.values());

        return "audit-update";
    }

    @GetMapping("/audits/{id}/update/errors")
    public String updateAuditErrors(@PathVariable Long id, Model model) {

        model.addAttribute("departments", DepartmentEnum.values());
        model.addAttribute("status", StatusAuditEnum.values());

        return "audit-update";
    }

    @PatchMapping("/audits/{id}/update")
    public String updateAudit(@PathVariable Long id,
                              @Valid AuditUpdateBindingModel auditUpdateBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("auditUpdateBindingModel", auditUpdateBindingModel);
            redirectAttributes.
                    addFlashAttribute("org.springframework.validation.BindingResult.auditUpdateBindingModel", bindingResult);

            return "redirect:/audits/" + id + "/update/errors";
        }

        AuditUpdateServiceModel auditUpdateServiceModel =
                modelMapper.map(auditUpdateBindingModel, AuditUpdateServiceModel.class);
        auditUpdateServiceModel.setId(id);

        auditService.updateAudit(auditUpdateServiceModel);

        return "redirect:/audits/" + id + "/details";
    }


    // DELETE

    @PreAuthorize("isOwner(#id)")
    @DeleteMapping("/audits/{id}")
    public String deleteAudit(@PathVariable Long id,
                              Principal principal) {

        auditService.deleteAudit(id);

        return "redirect:/users/home";
    }


}