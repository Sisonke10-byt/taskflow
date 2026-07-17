package com.sisonke.taskflow.dto.response;

import java.time.LocalDateTime;

import com.sisonke.taskflow.entity.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private LocalDateTime createdAt;

}
