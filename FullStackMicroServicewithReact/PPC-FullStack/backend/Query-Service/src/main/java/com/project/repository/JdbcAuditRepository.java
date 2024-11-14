package com.project.repository;

import com.project.entity.AuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcAuditRepository implements AuditRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AuditTrail> getAuditByReviewId(String reviewId) {
        String query = "SELECT * FROM AUDITTRAIL WHERE reviewId = ?";
        Object [] args = {reviewId};
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(AuditTrail.class),args);
    }
}
