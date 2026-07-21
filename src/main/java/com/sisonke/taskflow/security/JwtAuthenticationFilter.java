package com.sisonke.taskflow.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sisonke.taskflow.repository.UserRepository;
import com.sisonke.taskflow.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserRepository userRepository) {

        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        String email = jwtService.extractEmail(jwt);

        System.out.println("Email from JWT: " + email);

        User user = userRepository.findByEmail(email)
        .orElse(null);

        if (user != null && jwtService.validateToken(jwt, email)) {

    UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    Collections.emptyList()
            );

    SecurityContextHolder.getContext()
            .setAuthentication(authentication);

    System.out.println("User authenticated: " + email);
}

        filterChain.doFilter(request, response);
    }
}