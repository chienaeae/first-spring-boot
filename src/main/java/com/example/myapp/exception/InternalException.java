package com.example.myapp.exception;

public class InternalException extends GeneralException {

    public InternalException(String message) {
        super(message, null);
    }

    public InternalException(String message, String errorCode) {
        super(message, errorCode);
    }
}
