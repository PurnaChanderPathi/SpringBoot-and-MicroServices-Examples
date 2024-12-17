package com.project.repository;

import com.netflix.discovery.converters.Auto;
import com.project.entity.ResponseQueryDetails;
import com.project.entity.ResponseRemediation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

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

    @Override
    public void deleteResponse(String childReviewId) {
        String query = "DELETE FROM responseremediation where childReviewId = ? ";
        Object[] args = {childReviewId};
        jdbcTemplate.update(query,args);
    }

    @Override
    public void saveResponseQuery(ResponseQueryDetails responseQueryDetails) {
        String query = "INSERT INTO RESPONSEQUERY (querySequence,query,createdOn,createdBy,response,responseBy,responseOn,reviewId,childReviewId)VALUES(?,?,?,?,?,?,?,?,?)";
        Object[] args = {responseQueryDetails.getQuerySequence(),
                         responseQueryDetails.getQuery(),
                         responseQueryDetails.getCreatedOn(),
                         responseQueryDetails.getCreatedBy(),
                         responseQueryDetails.getResponse(),
                         responseQueryDetails.getResponseBy(),
                         responseQueryDetails.getResponseOn(),
                         responseQueryDetails.getReviewId(),
                         responseQueryDetails.getChildReviewId()
        };
        jdbcTemplate.update(query,args);
    }

    @Override
    public void deleteResponseQuery(String querySequence) {
        String query = "DELETE FROM responsequery where querySequence = ?";
        Object[] args = {querySequence};
        jdbcTemplate.update(query,args);
    }

}
