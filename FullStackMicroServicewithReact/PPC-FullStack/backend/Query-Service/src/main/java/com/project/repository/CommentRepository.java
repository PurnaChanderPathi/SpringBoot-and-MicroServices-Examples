package com.project.repository;

import com.project.entity.Comment;

import java.util.List;

public interface CommentRepository {

    Comment findByReviewId(String reviewId);
    List<Comment> getAllCommentByReviewId(String reviewId);
}
