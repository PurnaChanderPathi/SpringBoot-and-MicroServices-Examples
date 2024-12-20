package com.project.repository;

import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcObligorRepository implements ObligorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void saveObligor(Obligor obligor) {
        String query = "INSERT INTO OBLIGOR" +
                " (reviewId,obligorName,obligorCifId,obligorPremId,groupName,createdBy,createdOn," +
                "reviewStatus,childReviewId,division,isActive,observation,assignedTo,taskStatus,activityLevel)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                obligor.getAssignedTo(),
                obligor.getTaskStatus(),
                obligor.getActivityLevel()
        };
        jdbcTemplate.update(query,args);

    }


    @Override
    public Obligor updateObligor(Obligor obligor) {

        StringBuilder query = new StringBuilder("UPDATE OBLIGOR SET ");
        List<Object> args = new ArrayList<>();

        if(obligor.getReviewId() != null){
            query.append("reviewId=? ,");
            args.add(obligor.getReviewId());
        }

        if(obligor.getObligorName() != null){
            query.append("obligorName=? ,");
            args.add(obligor.getObligorName());
        }

        if(obligor.getObligorCifId() != null){
            query.append("obligorCifId=? ,");
            args.add(obligor.getObligorCifId());
        }

        if(obligor.getObligorPremId() != null){
            query.append("obligorPremId=? ,");
            args.add(obligor.getObligorPremId());
        }
        if(obligor.getGroupName() != null){
            query.append("groupName=? ,");
            args.add(obligor.getGroupName());
        }
        if(obligor.getCreatedBy() != null){
            query.append("createdBy=? ,");
            args.add(obligor.getCreatedBy());
        }
        if(obligor.getCreatedOn() != null){
            query.append("createdOn=? ,");
            args.add(obligor.getCreatedOn());
        }
        if(obligor.getReviewStatus() != null){
            query.append("reviewStatus=? ,");
            args.add(obligor.getReviewStatus());
        }
        if(obligor.getDivision() != null){
            query.append("division=? ,");
            args.add(obligor.getDivision());
        }
        if(obligor.getIsActive() != null){
            query.append("isActive=? ,");
            args.add(obligor.getIsActive());
        }
        if(obligor.getObservation() != null){
            query.append("observation=? ,");
            args.add(obligor.getObservation());
        }
        if(obligor.getAssignedTo() != null){
            query.append("assignedTo=? ,");
            args.add(obligor.getAssignedTo());
        }
        if(obligor.getTaskStatus() != null){
            query.append("taskStatus=? ,");
            args.add(obligor.getTaskStatus());
        }
        if(obligor.getActivityLevel() != null){
            query.append("activityLevel=? ,");
            args.add(obligor.getActivityLevel());
        }
        query.setLength(query.length() - 2);
        query.append(" WHERE childReviewid = ?");
        args.add(obligor.getChildReviewId());

        int result = jdbcTemplate.update(query.toString(), args.toArray());
        if (result > 0) {
            return obligor;
        } else {
            throw new RuntimeException("Update failed for queryDetails");
        }

    }

    @Override
    public void deleteobligor(String childReviewId) {
        String query = "DELETE FROM OBLIGOR WHERE childReviewId = ?";
        Object[] args = {childReviewId};
        jdbcTemplate.update(query,childReviewId);

    }

    @Override
    public void saveObligorDocument(ObligorDocument obligorDocument) {
        String query = "INSERT INTO OBLIGORDOCUMENT (reviewId,documentName,uploadedOn,uploadedBy,file)VALUES(?,?,?,?,?)";
        Object[] args = {obligorDocument.getReviewId(),
        obligorDocument.getDocumentName(),
        obligorDocument.getUploadedOn(),
        obligorDocument.getUploadedBy(),
        obligorDocument.getFile()};
        jdbcTemplate.update(query,args);
    }

    @Override
    public void deleteObligorDocument(String obligorDocId) {
        String query = "DELETE FROM OBLIGORDOCUMENT WHERE obligorDocId = ?";
        Object[] args = {obligorDocId};
        jdbcTemplate.update(query,obligorDocId);
    }
}
