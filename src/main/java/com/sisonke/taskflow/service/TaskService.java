package com.sisonke.taskflow.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sisonke.taskflow.dto.request.TaskRequest;
import com.sisonke.taskflow.dto.request.UpdateTaskRequest;
import com.sisonke.taskflow.dto.response.TaskResponse;
import com.sisonke.taskflow.entity.Task;
import com.sisonke.taskflow.entity.User;
import com.sisonke.taskflow.exception.TaskNotFoundException;
import com.sisonke.taskflow.repository.TaskRepository;
import com.sisonke.taskflow.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Create a new task
    public TaskResponse createTask(
            TaskRequest request,
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(false)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Task savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    // Get all tasks belonging to the logged-in user
    public List<TaskResponse> getUserTasks(String userEmail) {

        return taskRepository.findByUserEmail(userEmail)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get a specific task belonging to the logged-in user
    public TaskResponse getTaskById(
            Long taskId,
            String userEmail) {

        Task task = taskRepository
                .findByIdAndUserEmail(taskId, userEmail)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        return mapToResponse(task);
    }

    // Update a task belonging to the logged-in user
    public TaskResponse updateTask(
            Long taskId,
            UpdateTaskRequest request,
            String userEmail) {

        Task task = taskRepository
                .findByIdAndUserEmail(taskId, userEmail)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(request.isCompleted());

        Task updatedTask = taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    // Delete a task belonging to the logged-in user
    public void deleteTask(
            Long taskId,
            String userEmail) {

        Task task = taskRepository
                .findByIdAndUserEmail(taskId, userEmail)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        taskRepository.delete(task);
    }

    // Convert Task entity to TaskResponse DTO
    private TaskResponse mapToResponse(Task task) {

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .createdAt(task.getCreatedAt())
                .build();
    }
}