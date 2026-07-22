package com.sisonke.taskflow.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {

    private String message;
    private int status;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ValidationErrorResponse(
            String message,
            int status,
            Map<String, String> errors,
            LocalDateTime timestamp) {

        this.message = message;
        this.status = status;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
