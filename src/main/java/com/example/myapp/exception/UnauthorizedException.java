package com.example.myapp.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends GeneralException{
    public UnauthorizedException(String message) {
        super(message, null);
    }
    public UnauthorizedException(String message, String errorCode) {
        super(message, errorCode);
    }
}
