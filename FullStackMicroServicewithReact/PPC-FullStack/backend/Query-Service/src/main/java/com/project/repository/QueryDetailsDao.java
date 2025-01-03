package com.project.repository;

import com.project.Dto.QueryMultiSearch;
import com.project.entity.QueryDetails;
import lombok.Data;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QueryDetailsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(QueryDetailsDao.class);

    private static final String SELECT_QUERY = "SELECT * FROM querydetails " +
            "WHERE (? IS NULL OR reviewId = ?)";

    public List<QueryDetails> getQueryDetails( String reviewId) {
        return jdbcTemplate.query(SELECT_QUERY, new Object[]{
                reviewId, reviewId
        }, new RowMapper<QueryDetails>() {
            @Override
            public QueryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                QueryDetails queryDetails = new QueryDetails();
                queryDetails.setReviewId(rs.getString("reviewId"));
                queryDetails.setDivision(rs.getString("division"));
                queryDetails.setGroupName(rs.getString("groupName"));
                queryDetails.setAssignedTo(rs.getString("assignedTo"));
                queryDetails.setRole(rs.getString("role"));
                queryDetails.setCurrentStatus(rs.getString("currentStatus"));
                queryDetails.setCreatedDate(rs.getDate("createdDate"));
                queryDetails.setCreatedBy(rs.getString("createdBy"));
                queryDetails.setPlanning(rs.getString("planning"));
                queryDetails.setFieldwork(rs.getString("fieldwork"));
                queryDetails.setAction(rs.getString("action"));
                return queryDetails;
            }
        });
    }

    public QueryMultiSearch getMultiSearch(String reviewId, String childReviewId) {
        // Build the base query
        StringBuilder query = new StringBuilder("""
        SELECT 
            querydetails.division,
            querydetails.groupName,
            querydetails.assignedTo, 
            querydetails.`ROLE`, 
            querydetails.createdBy,
            querydetails.currentStatus,
            querydetails.reviewId,
            obligor.childReviewId
        FROM 
            querydetails 
        LEFT JOIN 
            obligor 
        ON 
            querydetails.reviewId = obligor.reviewId
        WHERE 
    """);

        // List to store query parameters
        List<Object> args = new ArrayList<>();

        // Build the query conditionally
        if (reviewId != null && childReviewId == null) {
            query.append("querydetails.reviewId = ?");
            args.add(reviewId);
        } else if (reviewId == null && childReviewId != null) {
            query.append("obligor.childReviewId = ?");
            args.add(childReviewId);
        } else if (reviewId != null && childReviewId != null) {
            query.append("querydetails.reviewId = ? AND obligor.childReviewId = ?");
            args.add(reviewId);
            args.add(childReviewId);
        } else {
            // If neither reviewId nor childReviewId is provided, throw an exception
            throw new IllegalArgumentException("At least one of reviewId or childReviewId must be provided.");
        }

        // Log the final query and arguments
        logger.info("Final query for getMultiSearch: {}", query);
        logger.info("Query parameters: {}", args);

        // Execute the query
        try {
            return jdbcTemplate.queryForObject(
                    query.toString(),
                    args.toArray(),
                    BeanPropertyRowMapper.newInstance(QueryMultiSearch.class)
            );
        } catch (Exception e) {
            logger.error("Error occurred while executing getMultiSearch: ", e);
            throw e;
        }
    }

}
