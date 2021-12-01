package com.example.scm_system.init;

import com.example.scm_system.service.RoleService;
import com.example.scm_system.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    public DBInit(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
        roleService.initializeRoles();
        userService.initializeAdmin();
    }
}