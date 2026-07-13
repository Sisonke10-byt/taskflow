package com.sisonke.taskflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sisonke.taskflow.dto.RegisterRequest;
import com.sisonke.taskflow.entity.User;
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
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterRequest request) {

        User savedUser = userService.registerUser(request);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
