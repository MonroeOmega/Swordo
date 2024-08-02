package com.example.swordo.service.impl;

import com.example.swordo.models.entity.User;
import com.example.swordo.models.entity.UserRoleEnum;
import com.example.swordo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //Note: Currently part of the user data is loaded separately. Will change that later if the need arises.
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User " + username + " not found"));

        return mapToUserDetails(user);
    }

    public UserDetails mapToUserDetails(User user){
        List<GrantedAuthority> grantedAuthorities =
                Arrays.stream(UserRoleEnum.values())
                        .map( role -> new SimpleGrantedAuthority("ROLE_"+ role))
                        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }
}
