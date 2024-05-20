package com.example.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{
    private HttpStatus errorCode;

    public AppException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode(){
        return errorCode;
    }
}
