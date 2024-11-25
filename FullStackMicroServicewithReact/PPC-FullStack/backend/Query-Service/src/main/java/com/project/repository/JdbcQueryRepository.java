package com.project.repository;

import com.project.entity.QueryDetails;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JdbcQueryRepository implements QueryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;



    public String generateReviewId() {
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date()); // Use YYYYMMDD format for the date
        String lastReviewIdQuery = "SELECT reviewId FROM querydetails WHERE reviewId LIKE 'PPC-%' ORDER BY reviewId DESC LIMIT 1";
        log.info("lastReviewIdQuery : {}",lastReviewIdQuery);

        String lastReviewId = null;

        try {
            lastReviewId = jdbcTemplate.queryForObject(lastReviewIdQuery, String.class);
        } catch (EmptyResultDataAccessException e) {
            lastReviewId = null; // No previous review found
        }

        // Start with a default value if no previous reviewId is found
        String newReviewId = "PPC-" + currentDate + "-001";

        if (lastReviewId != null) {
            // Extract the numeric part after the date (e.g., 001, 002, etc.)
            String lastNumericPart = lastReviewId.substring(lastReviewId.lastIndexOf('-') + 1);
            int newNumericPart = Integer.parseInt(lastNumericPart) + 1;  // Increment the numeric part
            newReviewId = "PPC-" + currentDate + "-" + String.format("%03d", newNumericPart);  // Format the new number as 3 digits
        }

        return newReviewId;
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
    public QueryDetails findByReviewId(String reviewId) {
        String query = "SELECT * from querydetails where reviewId = ?";
        Object [] args = {reviewId};
        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(QueryDetails.class),args);
    }


    @Override
    public List<QueryDetails> findAll() {
        String query = "SELECT * from querydetails";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(QueryDetails.class));
    }

//    @Override
//    public List<QueryDetails> findByRoleAndCreatedBy(List<String> role, String createdBy,String assignedTo) {
//        String query = "SELECT * " +
//                "FROM querydetails " +
//                "WHERE " +
//                "    (ROLE IS NULL OR TRIM(ROLE) IN (?)) " +
//                "    AND (createdBy IS NULL OR TRIM(createdBy) = ?) " +
//                "    AND (assignedTo IS NULL OR TRIM(assignedTo) = ?)";
//        Object[] args = new Object[role.size() + 2];
//        args[0] = String.join(",", role);
//        args[1] = createdBy != null ? createdBy : "";
//        args[2] = assignedTo != null ? assignedTo : "";
//
//        return jdbcTemplate.query(query,args,BeanPropertyRowMapper.newInstance(QueryDetails.class));
//    }

}
