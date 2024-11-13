package com.project.controller;

import com.project.entity.Comment;
import com.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/saveComment")
    public Map<String,Object> saveComment(@RequestBody Comment comment){
        Map<String,Object> response = new HashMap<>();
        String result = commentService.saveComment(comment);
        response.put("status", HttpStatus.OK.value());
        response.put("message","Comment Details Saved Successfully..!");
        return response;
    }

    @DeleteMapping("/delete/{viewComment}")
    public Map<String,Object> deleteComment(@PathVariable String viewComment){
        Map<String,Object> response = new HashMap<>();
        String result = commentService.deleteComment(viewComment);
        response.put("status", HttpStatus.OK.value());
        response.put("message","Comment Details Deleted Successfully..!");
        return response;
    }
}
