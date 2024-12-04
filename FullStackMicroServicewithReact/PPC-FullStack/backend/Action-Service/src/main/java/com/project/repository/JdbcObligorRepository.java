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
        String query = "INSERT INTO OBLIGOR" +
                " (reviewId,obligorName,obligorCifId,obligorPremId,groupName,createdBy,createdOn," +
                "reviewStatus,childReviewId,division,isActive,observation,taskStatus,activityLevel)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] args = {
                obligor.getReviewId(),
                obligor.getObligorName(),
                obligor.getObligorCifId(),
                obligor.getObligorPremId(),
                obligor.getGroupName(),
                obligor.getCreatedBy(),
                obligor.getCreatedOn(),
                obligor.getReviewStatus(),
                obligor.getChildReviewId(),
                obligor.getDivision(),
                obligor.getIsActive(),
                obligor.getObservation(),
                obligor.getTaskStatus(),
                obligor.getActivityLevel()
        };
        jdbcTemplate.update(query,args);

    }

//    private int obligorId;
//    private String reviewId;
//    private String obligorName;
//    private String obligorCifId;
//    private String obligorPremId;
//    private String groupName;
//    private String createdBy;
//    private String createdOn;
//    private String reviewStatus;
//    private String childReviewId;
//    private String division;
//    private String isActive;
//    private String observation;
//    private String taskStatus;
//    private String activityLevel;

    @Override
    public void updateObligor(Obligor obligor) {
        String query = "UPDATE OBLIGOR SET reviewId=? division=? obligorName=? premId=? cifId=? WHERE childReviewId=?";
        Object[] args = {obligor.getReviewId(),obligor.getDivision(),obligor.getObligorName(),
                obligor.getObligorPremId(),obligor.getObligorCifId(),obligor.getChildReviewId()};
        jdbcTemplate.update(query,args);
    }
}
