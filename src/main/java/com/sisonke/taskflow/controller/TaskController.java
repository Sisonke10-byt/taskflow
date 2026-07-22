package com.sisonke.taskflow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.sisonke.taskflow.dto.request.TaskRequest;
import com.sisonke.taskflow.dto.response.TaskResponse;
import com.sisonke.taskflow.service.TaskService;
import com.sisonke.taskflow.dto.request.UpdateTaskRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {

        String userEmail = authentication.getName();

        TaskResponse response =
                taskService.createTask(request, userEmail);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getUserTasks(
            Authentication authentication) {

        String userEmail = authentication.getName();

        List<TaskResponse> tasks =
                taskService.getUserTasks(userEmail);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
public ResponseEntity<TaskResponse> getTaskById(
        @PathVariable Long id,
        Authentication authentication) {

    String userEmail = authentication.getName();

    TaskResponse response =
            taskService.getTaskById(id, userEmail);

    return ResponseEntity.ok(response);
}

@PutMapping("/{id}")
public ResponseEntity<TaskResponse> updateTask(
        @PathVariable Long id,
        @Valid @RequestBody UpdateTaskRequest request,
        Authentication authentication) {

    String userEmail = authentication.getName();

    TaskResponse response =
            taskService.updateTask(
                    id,
                    request,
                    userEmail
            );

    return ResponseEntity.ok(response);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteTask(
        @PathVariable Long id,
        Authentication authentication) {

    String userEmail = authentication.getName();

    taskService.deleteTask(id, userEmail);

    return ResponseEntity.noContent().build();
}
}
