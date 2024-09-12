package com.purna.controller;

import com.purna.entity.PostDetails;
import com.purna.entity.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ControllerMicroserviceController {

    @Autowired
    private RestTemplate restTemplate;

    // Service Microservice URLs
    private static final String SERVICE_USER_URL = "http://localhost:8083/service/user";
    private static final String SERVICE_POST_URL = "http://localhost:8083/service/post";

    // Save UserDetails via Service Microservice
    @PostMapping("/user")
    public ResponseEntity<UserDetails> saveUser(@RequestBody UserDetails userDetails) {
        UserDetails savedUser = restTemplate.postForObject(SERVICE_USER_URL, userDetails, UserDetails.class);
        return ResponseEntity.ok(savedUser);
    }

    // Save PostDetails via Service Microservice
    @PostMapping("/post")
    public ResponseEntity<PostDetails> savePost(@RequestBody PostDetails postDetails) {
        PostDetails savedPost = restTemplate.postForObject(SERVICE_POST_URL, postDetails, PostDetails.class);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping("/get")
    public String getTest(){
        return "pawan";
    }
}
