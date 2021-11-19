package com.cs4374.finalproject.services;

import com.cs4374.finalproject.dataTransferObject.RegisterRequest;
import com.cs4374.finalproject.repositories.UserRepository;
import com.cs4374.finalproject.siteElements.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getPassword());
        userRepository.save(user);
    }
}
