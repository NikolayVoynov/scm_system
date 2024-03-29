package com.example.scm_system.model.binding;

import com.example.scm_system.model.validation.UniqueUsername;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginBindingModel {

    private String username;
    private String password;


    @NotBlank
    @Size(min = 3, max = 20)
    @UniqueUsername
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Size(min = 3, max = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}