package com.purna.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {
    @GetMapping("/submission")
    public ResponseEntity<String> homeController(){
        return new ResponseEntity<>("welcome to submission service", HttpStatus.OK);
    }
}
