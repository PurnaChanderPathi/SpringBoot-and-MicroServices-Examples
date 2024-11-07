package com.project.repository;

import com.project.entity.QueryDetails;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcQueryRepository implements QueryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;


    @Override
    public String save(QueryDetails queryDetails) {
        if (queryDetails.getCreatedDate() == null) {
            queryDetails.setCreatedDate(new Date());
        }
        String currentDate = new SimpleDateFormat("ddMMyyyy").format(queryDetails.getCreatedDate());

        String lastReviewIdQuery = "SELECT reviewId FROM querydetails WHERE reviewId LIKE 'PPC-" + currentDate + "-%' ORDER BY reviewId DESC LIMIT 1";

        String lastReviewId = null;

        try {
            lastReviewId = jdbcTemplate.queryForObject(lastReviewIdQuery, String.class);
        } catch (EmptyResultDataAccessException e) {
            lastReviewId = null;
        }

        String newReviewId = "PPC-" + currentDate + "-001";

        if (lastReviewId != null) {
            String lastNumericPart = lastReviewId.substring(lastReviewId.lastIndexOf('-') + 1);
            int newNumericPart = Integer.parseInt(lastNumericPart) + 1;
            newReviewId = "PPC-" + currentDate + "-" + String.format("%03d", newNumericPart);
        }
        queryDetails.setReviewId(newReviewId);

        String query = "INSERT INTO querydetails (reviewId,childReviewId, issueId, trackIssueId, division, groupName, taskStatus, assignedToUser, role,currentStatus,createdDate,createdBy) " +
                "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] args = {
                queryDetails.getReviewId(),
                queryDetails.getChildReviewId(),
                queryDetails.getIssueId(),
                queryDetails.getTrackIssueId(),
                queryDetails.getDivision(),
                queryDetails.getGroupName(),
                queryDetails.getTaskStatus(),
                queryDetails.getAssignedToUser(),
                queryDetails.getRole(),
                queryDetails.getCurrentStatus(),
                queryDetails.getCreatedDate(),
                queryDetails.getCreatedBy()
        };

        jdbcTemplate.update(query, args);
        return "QueryDetails inserted successfully";
    }


    @Override
    public QueryDetails saveQuery(QueryDetails queryDetails) {
        // Generate the date part of the unique identifier
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        // Get the next incrementing number for today's date
        int nextIncrement = getNextIncrement(datePart);
        // Generate the unique identifier
        String uniqueIdentifier = String.format("FCR-%s-%02d", datePart, nextIncrement);

        // Set the unique identifier as reviewId
        queryDetails.setReviewId(uniqueIdentifier);

        // Prepare the insert query including the uniqueIdentifier
        String query = "INSERT INTO querydetails (reviewId, childReviewId, issueId, trackIssueId, division, groupName, taskStatus, assignedToUser, role,currentStatus,createdDate,createdBy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
        Object[] args = {
                queryDetails.getReviewId(), // Insert the uniqueIdentifier here
                queryDetails.getChildReviewId(),
                queryDetails.getIssueId(),
                queryDetails.getTrackIssueId(),
                queryDetails.getDivision(),
                queryDetails.getGroupName(),
                queryDetails.getTaskStatus(),
                queryDetails.getAssignedToUser(),
                queryDetails.getRole(),
                queryDetails.getCurrentStatus(),
                queryDetails.getCreatedDate(),
                queryDetails.getCreatedBy()
        };

        // Execute the insert
        int saveQuery = jdbcTemplate.update(query, args);
        if (saveQuery > 0) {
            return queryDetails; // Return the populated QueryDetails object
        } else {
            throw new RuntimeException("Failed to save query details");
        }
    }


    private int getNextIncrement(String datePart) {
        // Query to find the highest increment for today's date
        String query = "SELECT COUNT(*) FROM querydetails WHERE reviewId LIKE ?";
        String pattern = "PPC-" + datePart + "-%"; // Pattern for today's date

        // Retrieve the count of existing entries for today and increment it
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{pattern}, Integer.class);
        return (count != null) ? count + 1 : 1;
    }

    @Override
    public int update(QueryDetails queryDetails) {
        String query = "UPDATE querydetails SET childReviewId=? issueId=? trackIssueId=?,division=? groupName=?,taskStatus=?,assignedToUser=?,role=?,currentStatus=?,createdDate=?,createdBy=? WHERE reviewId = ?";
        Object [] args = {queryDetails.getChildReviewId(),
                queryDetails.getIssueId(),
                queryDetails.getTrackIssueId(),
                queryDetails.getDivision(),
                queryDetails.getGroupName(),
                queryDetails.getTaskStatus(),
                queryDetails.getAssignedToUser(),
                queryDetails.getRole(),
                queryDetails.getReviewId(),
                queryDetails.getCurrentStatus(),
                queryDetails.getCreatedDate(),
                queryDetails.getCreatedBy()
        };
        return jdbcTemplate.update(query,args);
    }

    @Override
    public QueryDetails updateQuery(QueryDetails queryDetails) {
        String query = "UPDATE querydetails SET childReviewId=? issueId=? trackIssueId=?,division=? groupName=?,taskStatus=?,assignedToUser=?,role=?,currentStatus=?,createdDate=?,createdBy=? WHERE reviewId = ? ";
        Object [] args = {
                queryDetails.getChildReviewId(),
                queryDetails.getIssueId(),
                queryDetails.getTrackIssueId(),
                queryDetails.getDivision(),
                queryDetails.getGroupName(),
                queryDetails.getTaskStatus(),
                queryDetails.getAssignedToUser(),
                queryDetails.getRole(),
                queryDetails.getReviewId(),
                queryDetails.getCurrentStatus(),
                queryDetails.getCreatedDate(),
                queryDetails.getCreatedBy()
        };
        int result = jdbcTemplate.update(query,args);
        if(result > 0){
            return queryDetails;
        }else{
            throw new RuntimeException("update failed for queryDetails");
        }
    }

    @Override
    public QueryDetails findByReviewId(Long reviewId) {
        String query = "SELECT * from querydetails where reviewId = ?";
        Object [] args = {reviewId};
        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(QueryDetails.class),args);
    }

    @Override
    public int deleteByReviewId(Long reviewId) {
        String query = "DELETE from querydetails where reviewId = ?";
        Object [] args = {reviewId};
        return jdbcTemplate.update(query,args);
    }

    @Override
    public List<QueryDetails> findAll() {
        String query = "SELECT * from querydetails";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(QueryDetails.class));
    }

}
