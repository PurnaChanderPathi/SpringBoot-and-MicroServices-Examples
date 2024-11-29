package com.project.repository;

import com.project.entity.Obligor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcObligorRepository implements ObligorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveObligor(Obligor obligor) {
        String query = "INSERT INTO OBLIGOR (reviewId,childReviewId,division,obligorName,premId,cifId)VALUES(?,?,?,?,?,?)";
        Object[] args = {obligor.getReviewId(),obligor.getChildReviewId(),obligor.getDivision(),
                obligor.getObligorName(),obligor.getPremId(),obligor.getCifId()};
        jdbcTemplate.update(query,args);
    }

    @Override
    public void updateObligor(Obligor obligor) {
        String query = "UPDATE OBLIGOR SET reviewId=? division=? obligorName=? premId=? cifId=? WHERE childReviewId=?";
        Object[] args = {obligor.getReviewId(),obligor.getDivision(),obligor.getObligorName(),
                obligor.getPremId(),obligor.getCifId(),obligor.getChildReviewId()};
        jdbcTemplate.update(query,args);
    }
}
