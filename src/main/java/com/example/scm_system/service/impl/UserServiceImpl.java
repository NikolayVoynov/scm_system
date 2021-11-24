package com.example.scm_system.service.impl;

import com.example.scm_system.model.entity.RoleEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.RoleEnum;
import com.example.scm_system.model.service.UserRegistrationServiceModel;
import com.example.scm_system.repository.RoleRepository;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final SystemUserDetailsServiceImpl systemUserDetailsService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository, SystemUserDetailsServiceImpl systemUserDetailsService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.systemUserDetailsService = systemUserDetailsService;
    }

    @Override
    public boolean isUsernameFree(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel) {

        RoleEntity roleEntity = roleRepository.findByRole(RoleEnum.USER);

        UserEntity newUser = new UserEntity();

        newUser.setUsername(userRegistrationServiceModel.getUsername());
        newUser.setFirstName(userRegistrationServiceModel.getFirstName());
        newUser.setLastName(userRegistrationServiceModel.getLastName());
        newUser.setActive(true);
        newUser.setPassword(userRegistrationServiceModel.getPassword());
        newUser.setEmail(userRegistrationServiceModel.getEmail());
        newUser.setCompanyPosition(userRegistrationServiceModel.getCompanyPosition());
        //       TODO set profile photo
        newUser.setRoles(Set.of(roleEntity));

        newUser = userRepository.save(newUser);

        //Spring representation of a user

        UserDetails principal = systemUserDetailsService.loadUserByUsername(newUser.getUsername());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, newUser.getPassword(), principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
