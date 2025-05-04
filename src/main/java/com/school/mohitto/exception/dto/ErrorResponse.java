package com.school.mohitto.exception.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        boolean isSuccess,
        int status,
        String message,
        LocalDateTime timestamp
) {

    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(false,status.value(), message, LocalDateTime.now());
    }
}