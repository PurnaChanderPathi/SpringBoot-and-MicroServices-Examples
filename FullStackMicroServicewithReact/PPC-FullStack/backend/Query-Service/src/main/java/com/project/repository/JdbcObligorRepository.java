package com.project.repository;

import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class JdbcObligorRepository implements ObligorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Obligor> getObligorDetailsByReviewId(String reviewId) {
        String query = "SELECT * FROM OBLIGOR WHERE reviewId = ?";
        Object[] args = {reviewId};
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Obligor.class),args);
    }

    @Override
    public List<ObligorDocument> getObligorDocumentByReviewId(String reviewId) {
        String query = "SELECT * FROM OBLIGORDOCUMENT WHERE reviewId = ?";
        Object[] args = {reviewId};
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ObligorDocument.class),args);
    }
}
