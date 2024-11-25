package com.project.repository;

import com.project.entity.QueryDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Slf4j
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
        String query = "INSERT INTO querydetails (reviewId, division, groupName, assignedTo, role,currentStatus,createdDate,createdBy,planning,fieldwork) " +
                "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] args = {
                queryDetails.getReviewId(),
                queryDetails.getDivision(),
                queryDetails.getGroupName(),
                queryDetails.getAssignedTo(),
                queryDetails.getRole(),
                queryDetails.getCurrentStatus(),
                queryDetails.getCreatedDate(),
                queryDetails.getCreatedBy(),
                queryDetails.getPlanning(),
                queryDetails.getFieldwork()

        };

        jdbcTemplate.update(query, args);
        return "QueryDetails inserted successfully";
    }

    @Override
    public QueryDetails updateQuery(QueryDetails queryDetails) {

        StringBuilder query = new StringBuilder("UPDATE querydetails SET ");
        List<Object> args = new ArrayList<>();

        if (queryDetails.getDivision() != null) {
            query.append("division=?, ");
            args.add(queryDetails.getDivision());
        }
        if (queryDetails.getGroupName() != null) {
            query.append("groupName=?, ");
            args.add(queryDetails.getGroupName());
        }
        if (queryDetails.getAssignedTo() != null) {
            query.append("assignedTo=?, ");
            args.add(queryDetails.getAssignedTo());
        }
        if (queryDetails.getRole() != null) {
            query.append("role=?, ");
            args.add(queryDetails.getRole());
        }
        if (queryDetails.getCurrentStatus() != null) {
            query.append("currentStatus=?, ");
            args.add(queryDetails.getCurrentStatus());
        }
        if (queryDetails.getCreatedDate() != null) {
            query.append("createdDate=?, ");
            args.add(queryDetails.getCreatedDate());
        }
        if (queryDetails.getCreatedBy() != null) {
            query.append("createdBy=?, ");
            args.add(queryDetails.getCreatedBy());
        }
        if(queryDetails.getPlanning() != null){
            query.append("planning=?, ");
            args.add(queryDetails.getPlanning());
        }
        if(queryDetails.getFieldwork() != null){
            query.append("fieldwork=?, ");
            args.add(queryDetails.getFieldwork());
        }

        query.setLength(query.length() - 2); // Remove trailing ", "

        // Add the WHERE clause
        query.append(" WHERE reviewId = ?");
        args.add(queryDetails.getReviewId());

        // Execute the query
        int result = jdbcTemplate.update(query.toString(), args.toArray());
        if (result > 0) {
            return queryDetails;
        } else {
            throw new RuntimeException("Update failed for queryDetails");
        }
    }


    @Override
    public String deleteByReviewId(String reviewId) {
        String query = "DELETE from querydetails where reviewId = ?";
        Object [] args = {reviewId};
         jdbcTemplate.update(query,args);
         return "QueryDetails Deleted with reviewId : "+reviewId;
    }
}
