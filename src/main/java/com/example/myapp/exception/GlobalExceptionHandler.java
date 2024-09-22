package com.example.myapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(CustomNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode(), exception.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode(), exception.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode(), exception.getMessage(), null), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuilder errorDetails = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorDetails.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        });
        return new ResponseEntity<>(new ErrorResponse("validation failed", null, errorDetails.toString()), HttpStatus.BAD_REQUEST);
    }
}
