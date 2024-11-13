package com.project.repository;

import com.project.entity.Comment;

public interface CommentRepository {

    String saveComment(Comment comment);
    String deleteComment(String viewComment);
}
