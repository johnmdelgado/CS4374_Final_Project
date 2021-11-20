package com.cs4374.finalproject.controllers;


import com.cs4374.finalproject.dataTransferObject.LoginRequest;
import com.cs4374.finalproject.dataTransferObject.RegisterRequest;
import com.cs4374.finalproject.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Here is where we create new users. This endpoint is to create users
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        // This will return status code 200 for our api
        return new ResponseEntity(HttpStatus.OK);
    }

    // Once a user account has been created then we want to be able to
    // authenticate these users.
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
