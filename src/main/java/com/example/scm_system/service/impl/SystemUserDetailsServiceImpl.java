package com.example.scm_system.service.impl;


import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.exceptions.ObjectNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public SystemUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity =
                userRepository.findByUsername(username).
                        orElseThrow(() -> new ObjectNotFoundException("User with username " + username + " not found!"));

        UserDetails userDetails = mapUserEntityToUserDetails(userEntity);

        return userDetails;
    }

    private static UserDetails mapUserEntityToUserDetails(UserEntity userEntity) {

        List<GrantedAuthority> authorities =
                userEntity.
                        getRoles().
                        stream().
                        map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name())).
                        collect(Collectors.toList());


        SystemUserSpring systemUserSpring =
                new SystemUserSpring(userEntity.getUsername(), userEntity.getPassword(), authorities);

        return systemUserSpring;
    }
}
