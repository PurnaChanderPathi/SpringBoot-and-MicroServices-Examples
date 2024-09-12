package com.purna.controller;

import com.purna.entity.PostDetails;
import com.purna.entity.UserDetails;
import com.purna.repositories.PostDetailsRepository;
import com.purna.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PostDetailsRepository postDetailsRepository;

    // Save UserDetails
    @PostMapping("/user")
    public ResponseEntity<UserDetails> saveUser(@RequestBody UserDetails userDetails) {
        UserDetails savedUser = userDetailsRepository.save(userDetails);
        return ResponseEntity.ok(savedUser);
    }

    // Save PostDetails
    @PostMapping("/post")
    public ResponseEntity<PostDetails> savePost(@RequestBody PostDetails postDetails) {
        PostDetails savedPost = postDetailsRepository.save(postDetails);
        return ResponseEntity.ok(savedPost);
    }
}
