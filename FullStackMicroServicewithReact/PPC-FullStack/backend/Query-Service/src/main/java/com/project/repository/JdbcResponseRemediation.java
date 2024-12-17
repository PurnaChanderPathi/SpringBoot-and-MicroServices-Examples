package com.project.repository;

import com.project.entity.ObligorDocument;
import com.project.entity.ResponseQueryDetails;
import com.project.entity.ResponseRemediation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<ResponseQueryDetails> findResponseQueryByChildReviewId(String childReviewId) {
        String query = "SELECT * FROM RESPONSEQUERY WHERE childReviewId = ?";
        Object[] args = {childReviewId};
        try{
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResponseQueryDetails.class),args);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
