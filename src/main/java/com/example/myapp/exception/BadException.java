package com.example.myapp.exception;

public class BadException extends GeneralException {

    public BadException(String message) {
        super(message, null);
    }

    public BadException(String message, String errorCode) {
        super(message, errorCode);
    }
}
