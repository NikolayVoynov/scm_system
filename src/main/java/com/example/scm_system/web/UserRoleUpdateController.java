package com.example.scm_system.web;

import com.example.scm_system.model.binding.UserUpdateRoleBindingModel;
import com.example.scm_system.model.service.UserUpdateRoleServiceModel;
import com.example.scm_system.model.view.UserUpdateRoleViewModel;
import com.example.scm_system.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserRoleUpdateController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRoleUpdateController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("userUpdateRoleBindingModel")
    public UserUpdateRoleBindingModel userUpdateRoleBindingModel() {
        return new UserUpdateRoleBindingModel();
    }

    @GetMapping("/role-update")
    public String role(Model model) {

        List<UserUpdateRoleViewModel> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers", allUsers);

        return "role-update";
    }

    @PatchMapping("/role-update")
    public String updateRole(@Valid UserUpdateRoleBindingModel userUpdateRoleBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userUpdateRoleBindingModel", userUpdateRoleBindingModel);
            redirectAttributes.
                    addFlashAttribute("org.springframework.validation.BindingResult.userUpdateRoleBindingModel", bindingResult);

            return "redirect:/role-update";
        }


        UserUpdateRoleServiceModel userUpdateRoleServiceModel =
                modelMapper.map(userUpdateRoleBindingModel, UserUpdateRoleServiceModel.class);


        userService.updateUserRole(userUpdateRoleServiceModel);

        return "redirect:/";
    }
}
