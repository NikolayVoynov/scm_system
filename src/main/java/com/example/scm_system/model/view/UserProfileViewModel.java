package com.example.scm_system.model.view;

import com.example.scm_system.model.entity.ProfilePhotoEntity;
import com.example.scm_system.model.entity.RoleEntity;

import java.util.Set;

public class UserProfileViewModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String companyPosition;
    private ProfilePhotoEntity profilePhoto;

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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCompanyPosition() {
        return companyPosition;
    }

    public void setCompanyPosition(String companyPosition) {
        this.companyPosition = companyPosition;
    }

    public ProfilePhotoEntity getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(ProfilePhotoEntity profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
