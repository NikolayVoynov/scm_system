package com.example.scm_system.model.service;

import com.example.scm_system.model.entity.ProfilePhotosEntity;
import com.example.scm_system.model.entity.RoleEntity;

public class UserServiceModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private RoleEntity role;
    private String companyPosition;
    private ProfilePhotosEntity profilePhoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getCompanyPosition() {
        return companyPosition;
    }

    public void setCompanyPosition(String companyPosition) {
        this.companyPosition = companyPosition;
    }

    public ProfilePhotosEntity getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(ProfilePhotosEntity profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
