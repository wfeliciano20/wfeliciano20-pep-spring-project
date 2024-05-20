package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.Dtos.ErrorDto;
import com.example.exception.AppException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDto> handleException(AppException ex){
        ErrorDto errorDto = new ErrorDto(ex.getMessage(), ex.getErrorCode());
        return ResponseEntity.status(ex.getErrorCode()).body(errorDto);
    }
    
}
