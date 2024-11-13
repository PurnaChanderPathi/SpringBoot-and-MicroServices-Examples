package com.project.service;

import com.project.entity.Comment;
import com.project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment findByReviewId(String reviewId){
        return commentRepository.findByReviewId(reviewId);
    }

    public List<Comment> getAllCommentByReviewId(String reviewId){
        return commentRepository.getAllCommentByReviewId(reviewId);
    }
}
