package com.example.scm_system.model.binding;

import com.example.scm_system.model.entity.enums.RoleEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserUpdateRoleBindingModel {

    private String username;
    private RoleEnum role;

    @NotBlank
    @Size(min = 3, max = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}