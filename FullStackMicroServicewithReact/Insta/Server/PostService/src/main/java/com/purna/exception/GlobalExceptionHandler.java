package com.purna.exception;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {
//
//    @ExceptionHandler(WebClientResponseException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<ProblemDetail> handleWebClientResponseException(WebClientResponseException ex){
//        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
//        problemDetail.setProperty("errorMessage",ex.getMessage());
//        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
//        problemDetail.setType(URI.create("/error"));
//        return ResponseEntity.ok().body(problemDetail);
//    }
}
