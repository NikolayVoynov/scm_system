package com.example.scm_system.service;

import com.example.scm_system.model.service.UserRegistrationServiceModel;

public interface UserService {
    boolean isUsernameFree(String username);

    void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel);
}
