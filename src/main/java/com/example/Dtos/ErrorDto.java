package com.example.Dtos;

import org.springframework.http.HttpStatus;

public class ErrorDto {
    
    private String message;
    private HttpStatus errorCode;


    public ErrorDto(String message, HttpStatus errorCode){
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage(){
        return this.message;
    }

    public HttpStatus getErrorCode(){
        return this.errorCode;
    }
}
