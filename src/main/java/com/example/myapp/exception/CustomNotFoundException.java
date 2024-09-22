package com.example.myapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CustomNotFoundException extends GeneralException{
    public CustomNotFoundException(String message) {
        super(message, null);
    }

    public CustomNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }
}
