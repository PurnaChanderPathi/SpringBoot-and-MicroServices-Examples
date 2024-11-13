package com.project.controller;

import com.project.entity.Comment;
import com.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fetch")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/findByReviewId/{reviewId}")
    public Map<String,Object> findByReviewId(@PathVariable String reviewId){
        Map<String,Object> response = new HashMap<>();
        Comment result = commentService.findByReviewId(reviewId);
        if(result.getReviewId().isEmpty()){
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message","Comment Details not found with reviewId :"+reviewId);
        }else{
            response.put("status", HttpStatus.OK.value());
            response.put("message","Comment Details Fetched Successfully");
        }
        return response;
    }

    @GetMapping("/getAll/{reviewId}")
    public Map<String,Object> getAllCommentByReviewId(@PathVariable String reviewId){
        Map<String,Object> response = new HashMap<>();
        List<Comment> allComments = commentService.getAllCommentByReviewId(reviewId);
        response.put("status", HttpStatus.OK.value());
        response.put("message","All Comment Details Fetched Successfully");
        response.put("result",allComments);
        return response;
    }
}
