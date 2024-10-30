package com.project.repository;

import com.project.entity.QueryDetails;
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
            "WHERE (? IS NULL OR childReviewId = ?) " +
            "AND (? IS NULL OR reviewId = ?)";

    public List<QueryDetails> getQueryDetails(String childReviewId, String reviewId) {
        return jdbcTemplate.query(SELECT_QUERY, new Object[]{
                childReviewId, childReviewId,
                reviewId, reviewId
        }, new RowMapper<QueryDetails>() {
            @Override
            public QueryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                QueryDetails queryDetails = new QueryDetails();
                queryDetails.setReviewId(rs.getString("reviewId"));
                queryDetails.setChildReviewId(rs.getString("childReviewId"));
                queryDetails.setIssueId(rs.getString("issueId"));
                queryDetails.setTrackIssueId(rs.getString("trackIssueId"));
                queryDetails.setDivision(rs.getString("division"));
                queryDetails.setGroupName(rs.getString("groupName"));
                queryDetails.setTaskStatus(rs.getString("taskStatus"));
                queryDetails.setAssignedToUser(rs.getString("assignedToUser"));
                queryDetails.setRole(rs.getString("role"));
                return queryDetails;
            }
        });
    }
}
