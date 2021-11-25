package com.example.scm_system.service.impl;

import com.example.scm_system.model.entity.RoleEntity;
import com.example.scm_system.model.entity.enums.RoleEnum;
import com.example.scm_system.repository.RoleRepository;
import com.example.scm_system.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initializeRoles() {
        if (roleRepository.count() == 0) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setRole(RoleEnum.ADMIN);

            RoleEntity userRole = new RoleEntity();
            userRole.setRole(RoleEnum.USER);

            roleRepository.saveAll(List.of(adminRole, userRole));
        }
    }
}
