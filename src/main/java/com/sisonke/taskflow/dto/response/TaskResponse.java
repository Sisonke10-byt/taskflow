package com.sisonke.taskflow.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private boolean completed;

    private LocalDateTime createdAt;
}
