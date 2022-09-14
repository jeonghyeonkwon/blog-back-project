package com.jeonghyeon.blogapi.exception;


import org.springframework.http.HttpStatus;

public class ErrorResult {
    private int errorCode;
    private String message;
    public ErrorResult(HttpStatus httpStatus, String message){
        this.errorCode = httpStatus.value();
        this.message = message;
    }
}
