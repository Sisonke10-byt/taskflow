package com.sisonke.taskflow.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sisonke.taskflow.dto.request.RegisterRequest;
import com.sisonke.taskflow.dto.response.RegisterResponse;
import com.sisonke.taskflow.entity.Role;
import com.sisonke.taskflow.entity.User;
import com.sisonke.taskflow.repository.UserRepository;

import com.sisonke.taskflow.dto.request.LoginRequest;
import com.sisonke.taskflow.dto.response.LoginResponse;
import com.sisonke.taskflow.security.JwtService;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponse registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

    
        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
        .id(savedUser.getId())
        .firstName(savedUser.getFirstName())
        .lastName(savedUser.getLastName())
        .email(savedUser.getEmail())
        .role(savedUser.getRole())
        .createdAt(savedUser.getCreatedAt())
        .build();
    }

        public LoginResponse loginUser(LoginRequest request) {

        // Step 1: Find the user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Step 2: Check the password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Step 3: Generate a JWT
        String token = jwtService.generateToken(user.getEmail());

        // Step 4: Return the token
        return LoginResponse.builder()
                .token(token)
                .build();   
    }

}