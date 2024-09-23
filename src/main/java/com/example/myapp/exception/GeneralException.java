package com.example.myapp.exception;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{
    private final String errorCode;

    public GeneralException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
