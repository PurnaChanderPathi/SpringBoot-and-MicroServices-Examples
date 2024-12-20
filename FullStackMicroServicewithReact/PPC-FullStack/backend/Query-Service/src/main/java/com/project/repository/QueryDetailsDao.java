package com.project.repository;

import com.project.entity.QueryDetails;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QueryDetailsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
}
