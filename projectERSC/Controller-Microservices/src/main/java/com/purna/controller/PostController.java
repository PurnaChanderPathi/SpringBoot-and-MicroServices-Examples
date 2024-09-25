package com.purna.controller;

import com.purna.entity.PostDetails;
import com.purna.service.PostDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostDetailsService postDetailsService;

    @PostMapping("/savePost")
    public ResponseEntity<PostDetails> savePost(@RequestBody PostDetails postDetails){
        PostDetails savePost = postDetailsService.savePost(postDetails);
        return ResponseEntity.ok().body(savePost);
    }
}
