package com.project.repository;

import com.project.Dto.FileData;
import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;
import com.project.entity.ResponseRemediation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public List<ResponseRemediation> getResponseRemediationByReviewId(String reviewId) {
        String query = "SELECT * FROM RESPONSEREMEDIATION WHERE reviewId = ?";
        Object[] args = {reviewId};
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResponseRemediation.class),args);
    }

    @Override
    public ObligorDocument getObligorDocumentByobligorDocId(String obligorDocId) {
        String query = "SELECT * FROM OBLIGORDOCUMENT WHERE obligorDocId = ?";
        Object[] args = {obligorDocId};
        return jdbcTemplate.queryForObject(query,BeanPropertyRowMapper.newInstance(ObligorDocument.class),args);
    }

    @Override
    public Obligor findByChildReviewId(String childReviewId) {
        String query = "SELECT * FROM OBLIGOR WHERE childReviewId = ?";
        Object[] args = {childReviewId};
        return jdbcTemplate.queryForObject(query,BeanPropertyRowMapper.newInstance(Obligor.class),args);
    }

}
