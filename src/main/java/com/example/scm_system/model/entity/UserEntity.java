package com.example.scm_system.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Set<RoleEntity> roles;
    private String companyPosition;
//    private ProfilePhotosEntity profilePhoto;
    private boolean isActive;


    public UserEntity() {
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    @Column(nullable = false)
    public String getCompanyPosition() {
        return companyPosition;
    }

    public void setCompanyPosition(String companyPosition) {
        this.companyPosition = companyPosition;
    }

//    @OneToOne
//    public ProfilePhotosEntity getProfilePhoto() {
//        return profilePhoto;
//    }
//
//    public void setProfilePhoto(ProfilePhotosEntity profilePhoto) {
//        this.profilePhoto = profilePhoto;
//    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


}
