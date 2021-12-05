package com.example.scm_system.service;

import com.example.scm_system.model.service.UserRegistrationServiceModel;
import com.example.scm_system.model.service.UserUpdateRoleServiceModel;
import com.example.scm_system.model.view.UserProfileViewModel;
import com.example.scm_system.model.view.UserUpdateRoleViewModel;

import java.io.IOException;
import java.util.List;

public interface UserService {
    boolean isUsernameFree(String username);

    void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel) throws IOException;

    List<UserUpdateRoleViewModel> findAllUsers();

    void updateUserRole(UserUpdateRoleServiceModel userUpdateRoleServiceModel);

    void initializeAdmin();

    UserProfileViewModel findByUsername(String currentUser);
}
