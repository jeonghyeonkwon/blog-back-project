package com.jeonghyeon.blogapi.exception.advice;

import com.jeonghyeon.blogapi.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult illeagalState(IllegalStateException e){
        log.error(e.getMessage());
        return new ErrorResult(HttpStatus.BAD_REQUEST,e.getMessage());
    }
}
