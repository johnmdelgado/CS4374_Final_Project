package com.cs4374.finalproject.services;

import com.cs4374.finalproject.repositories.UserRepository;
import com.cs4374.finalproject.siteElements.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // let's get our user repository
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // try to find the user. If we are not able to find the user
        // then throw an error
        User user = userRepository.findByUserName(username).orElseThrow(()->
            new UsernameNotFoundException(username + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
            user.getPassword(),
            true,
            true,
            true,
            true,
            getAuthorities("ROLE_USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
        // Here we collect the granted authority for the role_user
        return Collections.singletonList(new SimpleGrantedAuthority(role_user));
    }
}
