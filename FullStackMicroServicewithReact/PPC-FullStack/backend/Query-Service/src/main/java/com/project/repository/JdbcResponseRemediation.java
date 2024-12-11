package com.project.repository;

import com.project.entity.ResponseRemediation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcResponseRemediation implements ResponseRemediationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResponseRemediation findByChildReviewId(String childReviewId) {
        String query = "SELECT * FROM RESPONSEREMEDIATION WHERE childReviewId = ?";
        Object[] args = {childReviewId};
        try{
            return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ResponseRemediation.class),args);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }
}
