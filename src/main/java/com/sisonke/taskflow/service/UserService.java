package com.sisonke.taskflow.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sisonke.taskflow.dto.request.RegisterRequest;
import com.sisonke.taskflow.dto.response.RegisterResponse;
import com.sisonke.taskflow.entity.Role;
import com.sisonke.taskflow.entity.User;
import com.sisonke.taskflow.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

}