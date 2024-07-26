package com.purna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNameOrOtpDoesnotMatchedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ProblemDetail> handleUserNameOrOtpDoesnotMatchedException(UserNameOrOtpDoesnotMatchedException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("errorMessage", ex.getMessage());
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("/error"));
        return ResponseEntity.ok().body(problemDetail);
    }

    @ExceptionHandler(NewPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ProblemDetail> handleNewPasswordException(NewPasswordException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("errorMessage", ex.getMessage());
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("/error"));
        return ResponseEntity.ok().body(problemDetail);
    }
}
