package com.project.repository;

import com.netflix.discovery.converters.Auto;
import com.project.entity.ResponseRemediation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcResponseRemediationRepository implements ResponseRemediationRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveResponse(ResponseRemediation responseRemediation) {

        String query = "INSERT INTO RESPONSEREMEDIATION" +
                " (reviewId,obligorName,obligorCifId,obligorPremId,groupName,createdBy,createdOn," +
                "reviewStatus,childReviewId,division,isActive,observation,taskStatus,activityLevel)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] args = {
                responseRemediation.getReviewId(),
                responseRemediation.getObligorName(),
                responseRemediation.getObligorCifId(),
                responseRemediation.getObligorPremId(),
                responseRemediation.getGroupName(),
                responseRemediation.getCreatedBy(),
                responseRemediation.getCreatedOn(),
                responseRemediation.getReviewStatus(),
                responseRemediation.getChildReviewId(),
                responseRemediation.getDivision(),
                responseRemediation.getIsActive(),
                responseRemediation.getObservation(),
                responseRemediation.getTaskStatus(),
                responseRemediation.getActivityLevel()
        };
         jdbcTemplate.update(query,args);
    }
}
