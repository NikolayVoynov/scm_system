package com.example.scm_system.service.impl;

import com.example.scm_system.model.entity.ProfilePhotoEntity;
import com.example.scm_system.model.entity.RoleEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.RoleEnum;
import com.example.scm_system.model.service.UserRegistrationServiceModel;
import com.example.scm_system.model.service.UserUpdateRoleServiceModel;
import com.example.scm_system.model.view.UserProfileViewModel;
import com.example.scm_system.model.view.UserUpdateRoleViewModel;
import com.example.scm_system.repository.ProfilePhotoRepository;
import com.example.scm_system.repository.RoleRepository;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.CloudinaryImage;
import com.example.scm_system.service.CloudinaryService;
import com.example.scm_system.service.UserService;
import com.example.scm_system.service.exceptions.ObjectNotFoundException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfilePhotoRepository profilePhotoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final SystemUserDetailsServiceImpl systemUserDetailsService;
    private final CloudinaryService cloudinaryService;

    public UserServiceImpl(UserRepository userRepository, ProfilePhotoRepository profilePhotoRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepository roleRepository, SystemUserDetailsServiceImpl systemUserDetailsService, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.profilePhotoRepository = profilePhotoRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.systemUserDetailsService = systemUserDetailsService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public boolean isUsernameFree(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isEmpty();
    }

    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel) throws IOException {

        if (userRepository.count() == 0) {
            RoleEntity roleEntity = roleRepository.findByRole(RoleEnum.ADMIN);

            registerDependingOnRole(userRegistrationServiceModel, roleEntity);
        } else {
            RoleEntity roleEntity = roleRepository.findByRole(RoleEnum.USER);

            registerDependingOnRole(userRegistrationServiceModel, roleEntity);
        }
    }

    private void registerDependingOnRole(UserRegistrationServiceModel userRegistrationServiceModel,
                                         RoleEntity roleEntity) throws IOException {


        ProfilePhotoEntity profilePhotoEntity = createProfilePhotoEntity(userRegistrationServiceModel.getProfilePhoto());
        profilePhotoRepository.save(profilePhotoEntity);
        ProfilePhotoEntity savedProfilePhotoEntity = profilePhotoRepository.findByUrl(profilePhotoEntity.getUrl());

        UserEntity newUser = new UserEntity();

        newUser.setUsername(userRegistrationServiceModel.getUsername());
        newUser.setFirstName(userRegistrationServiceModel.getFirstName());
        newUser.setLastName(userRegistrationServiceModel.getLastName());
        newUser.setActive(true);
        newUser.setPassword(passwordEncoder.encode(userRegistrationServiceModel.getPassword()));
        newUser.setEmail(userRegistrationServiceModel.getEmail());
        newUser.setCompanyPosition(userRegistrationServiceModel.getCompanyPosition());
        newUser.setProfilePhoto(savedProfilePhotoEntity);
        newUser.setRoles(Set.of(roleEntity));

        newUser = userRepository.save(newUser);

        //Spring representation of a user

        UserDetails principal = systemUserDetailsService.loadUserByUsername(newUser.getUsername());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, newUser.getPassword(), principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private ProfilePhotoEntity createProfilePhotoEntity(MultipartFile profilePhoto) throws IOException {
        CloudinaryImage uploaded = cloudinaryService.upload(profilePhoto);

        ProfilePhotoEntity profilePhotoEntity = new ProfilePhotoEntity();
        profilePhotoEntity.setPublicId(uploaded.getPublicId());
        profilePhotoEntity.setUrl(uploaded.getUrl());

        return profilePhotoEntity;
    }

    @Override
    public List<UserUpdateRoleViewModel> findAllUsers() {
        return userRepository.
                findAll().
                stream().
                map(userEntity -> modelMapper.map(userEntity, UserUpdateRoleViewModel.class)).
                collect(Collectors.toList());


    }

    @Override
    public void updateUserRole(UserUpdateRoleServiceModel userUpdateRoleServiceModel) {

        UserEntity currentUserEntity = userRepository.findByUsername(userUpdateRoleServiceModel.getUsername()).orElseThrow(() ->
                new ObjectNotFoundException("User with username " + userUpdateRoleServiceModel.getUsername() + " not found!"));
        RoleEntity newRole = roleRepository.findByRole(userUpdateRoleServiceModel.getRole());

        List<RoleEntity> currentRoles = currentUserEntity.getRoles().stream().toList();

        if (!currentRoles.contains(newRole)) {
            currentUserEntity.getRoles().add(newRole);
        }

        userRepository.save(currentUserEntity);
    }

    @Override
    public UserProfileViewModel findByUsername(String currentUser) {
        UserEntity userEntity = userRepository.findByUsername(currentUser).orElseThrow(() ->
                new ObjectNotFoundException("User with username " + currentUser + " not found!"));

        return modelMapper.map(userEntity, UserProfileViewModel.class);
    }
}
