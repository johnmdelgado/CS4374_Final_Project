package com.cs4374.finalproject.services;

import com.cs4374.finalproject.dataTransferObject.RegisterRequest;
import com.cs4374.finalproject.repositories.UserRepository;
import com.cs4374.finalproject.siteElements.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEmail(registerRequest.getPassword());
        userRepository.save(user);
    }

    // Here we don't want clear text passwords in the database for security reasons
    // what we'll do is encrypt the password that the user provided

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
