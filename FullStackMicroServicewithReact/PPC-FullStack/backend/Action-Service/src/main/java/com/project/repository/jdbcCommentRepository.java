package com.project.repository;

import com.project.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;

@Repository
public class jdbcCommentRepository implements CommentRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String saveComment(Comment comment) {
        if(comment.getCommentedOn() == null){
            comment.setCommentedOn(new Date());
        }
        String query = "INSERT INTO COMMENT (reviewId,commentedBy,commentedOn,viewComment)VALUES(?,?,?,?)";
        Object [] args = {
                comment.getReviewId(),
                comment.getCommentedBy(),
                comment.getCommentedOn(),
                comment.getViewComment()
        };
        jdbcTemplate.update(query,args);
        return "Comment Details saved Successfully";
    }

    @Override
    public String deleteComment(String viewComment) {

        String query = "DELETE FROM COMMENT WHERE viewComment = ?";
        Object [] args = {viewComment};
        jdbcTemplate.update(query,args);
        return "Comment Details deleted with reviewId :"+viewComment;
    }
}
