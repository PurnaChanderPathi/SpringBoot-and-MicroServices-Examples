package com.project.repository;

import com.project.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class jdbcCommentRepository implements CommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Comment findByReviewId(String reviewId) {
        String query = "SELECT * FROM COMMENT WHERE reviewId = ?";
        Object [] args = {reviewId};
        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Comment.class),args);
    }

    @Override
    public List<Comment> getAllCommentByReviewId(String reviewId) {
        String query = "SELECT * FROM COMMENT WHERE reviewId=?";
        Object [] args = {reviewId};
        return jdbcTemplate.query(query,BeanPropertyRowMapper.newInstance(Comment.class),args);
    }
}



