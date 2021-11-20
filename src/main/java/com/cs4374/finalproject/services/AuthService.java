package com.cs4374.finalproject.services;

import com.cs4374.finalproject.dataTransferObject.LoginRequest;
import com.cs4374.finalproject.dataTransferObject.RegisterRequest;
import com.cs4374.finalproject.repositories.UserRepository;
import com.cs4374.finalproject.security.JwtProvider;
import com.cs4374.finalproject.siteElements.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEmail(registerRequest.getPassword());
        userRepository.save(user);
    }

    // Here we don't want clear text passwords in the database for security reasons
    // what we'll do is encrypt the password that the user provided

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }
}
