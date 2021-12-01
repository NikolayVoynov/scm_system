package com.example.scm_system.model.service;

import com.example.scm_system.model.entity.enums.RoleEnum;

public class UserUpdateRoleServiceModel {

    private String username;
    private RoleEnum role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
