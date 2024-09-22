package com.example.myapp.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErrorResponse (
        String errorCode,
        String message,
        String errorDetails,
        LocalDateTime loggedAt
) {
    public ErrorResponse(String errorCode, String message, String errorDetails) {
        this(errorCode, message , errorDetails, LocalDateTime.now());
    }
}
