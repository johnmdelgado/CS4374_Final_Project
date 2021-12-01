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

import java.util.Optional;

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
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);
    }

    // Here we don't want clear text passwords in the database for security reasons
    // what we'll do is encrypt the password that the user provided

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authenticationToken = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }


    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        System.out.println("We've entered the getCurrentUserMethod");
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User)SecurityContextHolder.
                        getContext().getAuthentication().getPrincipal();
        System.out.println("Here is our Principal: " + principal);
        return Optional.of(principal);
    }
}
