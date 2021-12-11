package com.example.scm_system.web;

import com.example.scm_system.model.binding.AuditUpdateBindingModel;
import com.example.scm_system.model.binding.NonconformityAddBindingModel;
import com.example.scm_system.model.binding.NonconformityUpdateBindingModel;
import com.example.scm_system.model.entity.enums.DepartmentEnum;
import com.example.scm_system.model.entity.enums.LevelNonconformityEnum;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;
import com.example.scm_system.model.entity.enums.StatusNonconformityEnum;
import com.example.scm_system.model.service.NonconformityAddServiceModel;
import com.example.scm_system.model.service.NonconformityUpdateServiceModel;
import com.example.scm_system.model.view.NonconformityDetailsViewModel;
import com.example.scm_system.service.AuditService;
import com.example.scm_system.service.NonconformityService;
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
public class NonconformityController {

    private final NonconformityService nonconformityService;
    private final AuditService auditService;
    private final ModelMapper modelMapper;

    public NonconformityController(NonconformityService nonconformityService, AuditService auditService, ModelMapper modelMapper) {
        this.nonconformityService = nonconformityService;
        this.auditService = auditService;
        this.modelMapper = modelMapper;
    }

    // ADD

    @GetMapping("/nonconformities/add")
    public String getNonconformityAddPage(Model model) {

        if (!model.containsAttribute("nonconformityAddBindingModel")) {
            model
                    .addAttribute("nonconformityAddBindingModel", new NonconformityAddBindingModel())
                    .addAttribute("auditsModels", auditService.getAllAudits());
        }

        return "nonconformity-add";
    }

    @PostMapping("/nonconformities/add")
    public String addNonconformity(@Valid NonconformityAddBindingModel nonconformityAddBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal SystemUserSpring systemUserSpring) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("nonconformityAddBindingModel", nonconformityAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.nonconformityAddBindingModel", bindingResult)
                    .addFlashAttribute("auditsModels", auditService.getAllAudits());

            return "redirect:/nonconformities/add";
        }

        NonconformityAddServiceModel nonconformityAddServiceModel =
                nonconformityService.addNonconformity(nonconformityAddBindingModel, systemUserSpring.getUserIdentifier());

        return "redirect:/nonconformities/" + nonconformityAddServiceModel.getId() + "/details";
    }

    // DETAILS

    @GetMapping("/nonconformities/{id}/details")
    public String showNonconformity(@PathVariable Long id, Model model, Principal principal) {

        model.addAttribute("nonconformity", nonconformityService.findById(id, principal.getName()));

        return "nonconformity-details";
    }

    // UPDATE

    @GetMapping("/nonconformities/{id}/update")
    public String updateNonconformity(@PathVariable Long id, Model model, @AuthenticationPrincipal SystemUserSpring systemUserSpring) {

        NonconformityDetailsViewModel nonconformityDetailsViewModel = nonconformityService.findById(id, systemUserSpring.getUserIdentifier());
        NonconformityUpdateBindingModel nonconformityUpdateBindingModel =
                modelMapper.map(nonconformityDetailsViewModel, NonconformityUpdateBindingModel.class);

        model.addAttribute("nonconformityUpdateBindingModel", nonconformityUpdateBindingModel);
        model.addAttribute("status", StatusNonconformityEnum.values());
        model.addAttribute("level", LevelNonconformityEnum.values());
        model.addAttribute("auditsModels", auditService.getAllAudits());

        return "nonconformity-update";
    }

    @GetMapping("/nonconformities/{id}/update/errors")
    public String updateNonconformityErrors(@PathVariable Long id, Model model) {

        model.addAttribute("status", StatusNonconformityEnum.values());
        model.addAttribute("level", LevelNonconformityEnum.values());

        return "nonconformity-update";
    }

    @PatchMapping("/nonconformities/{id}/update")
    public String updateNonconformity(@PathVariable Long id,
                                      @Valid NonconformityUpdateBindingModel nonconformityUpdateBindingModel,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("nonconformityUpdateBindingModel", nonconformityUpdateBindingModel);
            redirectAttributes.
                    addFlashAttribute("org.springframework.validation.BindingResult.nonconformityUpdateBindingModel", bindingResult);

            return "redirect:/nonconformities/" + id + "/update/errors";
        }

        NonconformityUpdateServiceModel nonconformityUpdateServiceModel =
                modelMapper.map(nonconformityUpdateBindingModel, NonconformityUpdateServiceModel.class);
        nonconformityUpdateServiceModel.setId(id);

        nonconformityService.updateNonconformity(nonconformityUpdateServiceModel);

        return "redirect:/nonconformities/" + id + "/details";
    }


    // DELETE

    @PreAuthorize("@nonconformityServiceImpl.isOwner(#principal.name, #id)")
    @DeleteMapping("/nonconformities/{id}/delete")
    public String deleteNonconformity(@PathVariable Long id,
                              Principal principal) {

        if (!nonconformityService.isOwner(principal.getName(), id)) {
            throw new RuntimeException();
        }

        nonconformityService.deleteNonconformity(id);

        return "redirect:/dashboard";
    }

}
