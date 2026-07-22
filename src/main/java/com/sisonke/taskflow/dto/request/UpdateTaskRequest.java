package com.sisonke.taskflow.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private boolean completed;
}
