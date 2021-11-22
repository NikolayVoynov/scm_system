package com.example.scm_system.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private RoleEntity role;
    private String companyPosition;
//    private ProfilePhotosEntity profilePhoto;

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

    @ManyToOne
    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
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
}
