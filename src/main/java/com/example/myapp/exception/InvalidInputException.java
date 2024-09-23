package com.example.myapp.exception;

import lombok.Getter;

@Getter
public class InvalidInputException extends GeneralException{
    public InvalidInputException(String message) {
        super(message, null);
    }

    public InvalidInputException(String message, String errorCode) {
        super(message, errorCode);
    }
}
