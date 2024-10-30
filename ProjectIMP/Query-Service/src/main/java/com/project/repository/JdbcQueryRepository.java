package com.project.repository;

import com.project.entity.QueryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcQueryRepository implements QueryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(QueryDetails queryDetails) {
        String query = "INSERT into querydetails (childReviewId,issueId,trackIssueId,division,groupName,taskStatus,assignedToUser,role)values(?,?,?,?,?,?,?,?)";
        Object [] args = {queryDetails.getChildReviewId(),queryDetails.getIssueId(),queryDetails.getTrackIssueId(),queryDetails.getDivision(),queryDetails.getGroupName(),queryDetails.getTaskStatus(),queryDetails.getAssignedToUser(),queryDetails.getRole()};
        return jdbcTemplate.update(query,args);
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
        String query = "INSERT INTO querydetails (reviewId, childReviewId, issueId, trackIssueId, division, groupName, taskStatus, assignedToUser, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {
                queryDetails.getReviewId(), // Insert the uniqueIdentifier here
                queryDetails.getChildReviewId(),
                queryDetails.getIssueId(),
                queryDetails.getTrackIssueId(),
                queryDetails.getDivision(),
                queryDetails.getGroupName(),
                queryDetails.getTaskStatus(),
                queryDetails.getAssignedToUser(),
                queryDetails.getRole()
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
        String pattern = "FCR-" + datePart + "-%"; // Pattern for today's date

        // Retrieve the count of existing entries for today and increment it
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{pattern}, Integer.class);
        return (count != null) ? count + 1 : 1;
    }

    @Override
    public int update(QueryDetails queryDetails) {
        String query = "UPDATE querydetails SET childReviewId=? issueId=? trackIssueId=?,division=? groupName=?,taskStatus=?,assignedToUser=?,role=? WHERE reviewId = ?";
        Object [] args = {queryDetails.getChildReviewId(),queryDetails.getIssueId(),queryDetails.getTrackIssueId(),queryDetails.getDivision(),queryDetails.getGroupName(),queryDetails.getTaskStatus(),queryDetails.getAssignedToUser(),queryDetails.getRole(),queryDetails.getReviewId()};
        return jdbcTemplate.update(query,args);
    }

    @Override
    public QueryDetails updateQuery(QueryDetails queryDetails) {
        String query = "UPDATE querydetails SET childReviewId=? issueId=? trackIssueId=?,division=? groupName=?,taskStatus=?,assignedToUser=?,role=? WHERE reviewId = ? ";
        Object [] args = {queryDetails.getChildReviewId(),queryDetails.getIssueId(),queryDetails.getTrackIssueId(),queryDetails.getDivision(),queryDetails.getGroupName(),queryDetails.getTaskStatus(),queryDetails.getAssignedToUser(),queryDetails.getRole(),queryDetails.getReviewId()};
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

    private String generateUniqueIdentifier() {
        LocalDate currentDate = LocalDate.now();
        String datePart = currentDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        // Generate a unique identifier based on the current date
        // You might want to ensure that this is truly unique across all inserts
        return String.format("FCR-%s-%s", datePart, UUID.randomUUID().toString());
    }
}
