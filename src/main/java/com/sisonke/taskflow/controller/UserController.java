package com.sisonke.taskflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisonke.taskflow.dto.request.LoginRequest;
import com.sisonke.taskflow.dto.request.RegisterRequest;
import com.sisonke.taskflow.dto.response.LoginResponse;
import com.sisonke.taskflow.dto.response.RegisterResponse;
import com.sisonke.taskflow.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = userService.registerUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = userService.loginUser(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                "You are authenticated! Your email is: " + email
        );
    }
}