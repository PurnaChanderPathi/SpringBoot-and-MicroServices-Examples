package com.purna.controller;

import com.purna.entity.PostDetails;
import com.purna.entity.UserDetails;
import com.purna.service.PostDetailsService;
import com.purna.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PostDetailsService postDetailsService;

    @PostMapping("/user")
    public ResponseEntity<UserDetails> saveUser(@RequestBody UserDetails userDetails) {
        UserDetails savedUser = userDetailsService.saveUser(userDetails);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/post")
    public ResponseEntity<PostDetails> savePost(@RequestBody PostDetails postDetails) {
        PostDetails savedPost = postDetailsService.savePost(postDetails);
        return ResponseEntity.ok(savedPost);
    }
}

