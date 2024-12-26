package com.project.repository;

import com.netflix.discovery.converters.Auto;
import com.project.Dto.ResponseQueryDto;
import com.project.entity.ResponseQueryDetails;
import com.project.entity.ResponseRemediation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseQueryDto updateResponse(ResponseQueryDto responseQueryDto) {
        Timestamp responseOn = new Timestamp(System.currentTimeMillis());
        responseQueryDto.setResponseOn(responseOn);

        StringBuilder query = new StringBuilder("UPDATE RESPONSEQUERY SET ");
        List<Object> args = new ArrayList<>();

        boolean isFirst = true;

        if (responseQueryDto.getQuery() != null) {
            if (!isFirst) {
                query.append(", ");
            }
            query.append("query=?");
            args.add(responseQueryDto.getQuery());
            isFirst = false;
        }

        if (responseQueryDto.getResponse() != null) {
            if (!isFirst) {
                query.append(", ");
            }
            query.append("response=?");
            args.add(responseQueryDto.getResponse());
            isFirst = false;
        }

        if (responseQueryDto.getResponseBy() != null) {
            if (!isFirst) {
                query.append(", ");
            }
            query.append("responseBy=?");
            args.add(responseQueryDto.getResponseBy());
            isFirst = false;
        }

        if (responseQueryDto.getResponseOn() != null) {
            if (!isFirst) {
                query.append(", ");
            }
            query.append("responseOn=?");
            args.add(responseQueryDto.getResponseOn());
            isFirst = false;
        }

        query.append(" WHERE querySequence = ?");
        args.add(responseQueryDto.getQuerySequence());

        int result = jdbcTemplate.update(query.toString(), args.toArray());

        if (result > 0) {
            return responseQueryDto;
        } else {
            throw new RuntimeException("Update failed for responseQueryDetails");
        }
    }



    @Override
    public void deleteResponseQuery(String querySequence) {
        String query = "DELETE FROM responsequery where querySequence = ?";
        Object[] args = {querySequence};
        jdbcTemplate.update(query,args);
    }

}
