package com.example.scm_system.service.impl;

import com.example.scm_system.model.entity.RoleEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.RoleEnum;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class SystemUserDetailsServiceImplTest {

    private UserEntity testUser;
    private RoleEntity adminRole;
    private RoleEntity userRole;
    private SystemUserDetailsServiceImpl serviceToBeTested;

    @Mock
    private UserRepository mockUserRepository;


    @BeforeEach
    void init() {

        // ARRANGE
        serviceToBeTested = new SystemUserDetailsServiceImpl(mockUserRepository);

        adminRole = new RoleEntity();
        adminRole.setRole(RoleEnum.ADMIN);

        userRole = new RoleEntity();
        userRole.setRole(RoleEnum.USER);

        testUser = new UserEntity();
        testUser.setUsername("nikolay");
        testUser.setPassword("12345");
        testUser.setRoles(Set.of(adminRole, userRole));
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> serviceToBeTested.loadUserByUsername("invalidUsername"));
    }

    @Test
    void testUserFound() {

        // Arrange
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        // Act
        var actual = serviceToBeTested.loadUserByUsername(testUser.getUsername());

        //Assert
        String expectedRoles = "ROLE_ADMIN, ROLE_USER";
        String actualRoles =
                actual.getAuthorities().
                        stream().
                        map(GrantedAuthority::getAuthority).
                        collect(Collectors.joining(", "));

        Assertions.assertEquals(actual.getUsername(), testUser.getUsername());
        Assertions.assertEquals(expectedRoles, actualRoles);


    }
}
