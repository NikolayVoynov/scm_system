package com.example.scm_system.service;

import com.example.scm_system.model.service.UserServiceModel;

public interface UserService {
    boolean isUsernameFree(String username);

    void registerUser(UserServiceModel userServiceModel);
}
