package com.project.service;

import com.project.Dto.MultiTableSearch;
import com.project.entity.Obligor;
import com.project.entity.QueryDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MultiTableSearchService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<MultiTableSearch> getCombinedData(String assignedTo) {

        String querydetailsSql = "SELECT * FROM querydetails WHERE role is NOT NULL AND assignedTo = ?";

//        String obligorSql = "SELECT * FROM OBLIGOR WHERE role IS NOT NULL AND assignedTo = ?";
        String obligorSql = "SELECT * FROM OBLIGOR WHERE role is NOT NULL AND assignedTo =? AND reviewStatus != 'FieldWorkCompleted' ";
        Object[] args = {assignedTo};

        List<QueryDetails> queryDetails = jdbcTemplate.query(querydetailsSql, BeanPropertyRowMapper.newInstance(QueryDetails.class), args);
        log.info("Query Details : {}", queryDetails);
        List<Obligor> obligorDetails = jdbcTemplate.query(obligorSql, BeanPropertyRowMapper.newInstance(Obligor.class), assignedTo);
        log.info("Obligor Details : {}", obligorDetails);

        List<MultiTableSearch> combinedData = combineData(queryDetails, obligorDetails);
        log.info("combinedData : {}", combinedData);

        return combinedData;

    }

    private List<MultiTableSearch> combineData(List<QueryDetails> queryDetailsRecords, List<Obligor> obligorRecords) {
        List<MultiTableSearch> combinedList = new ArrayList<>();


        for (int i = 0; i < queryDetailsRecords.size(); i++) {
            MultiTableSearch multiTableSearchForQuery = new MultiTableSearch();
            QueryDetails getQueryData = queryDetailsRecords.get(i);
            multiTableSearchForQuery.setReviewId(getQueryData.getReviewId());
            multiTableSearchForQuery.setDivision(getQueryData.getDivision());
            multiTableSearchForQuery.setGroupName(getQueryData.getGroupName());
            multiTableSearchForQuery.setAssignedTo(getQueryData.getAssignedTo());
            multiTableSearchForQuery.setRole(getQueryData.getRole());
            multiTableSearchForQuery.setCurrentStatus(getQueryData.getCurrentStatus());
            multiTableSearchForQuery.setCreatedOn((Timestamp) getQueryData.getCreatedDate());
            multiTableSearchForQuery.setCreatedBy(getQueryData.getCreatedBy());
            multiTableSearchForQuery.setChildReviewId("");
            combinedList.add(multiTableSearchForQuery);
        }
        for (int p = 0; p < obligorRecords.size(); p++) {
            MultiTableSearch multiTableSearchForObligor = new MultiTableSearch();
            Obligor getObligorData = obligorRecords.get(p);
            multiTableSearchForObligor.setReviewId(getObligorData.getReviewId());
            multiTableSearchForObligor.setGroupName(getObligorData.getGroupName());
            multiTableSearchForObligor.setCreatedBy(getObligorData.getCreatedBy());
            multiTableSearchForObligor.setCreatedOn(getObligorData.getCreatedOn());
            multiTableSearchForObligor.setDivision(getObligorData.getDivision());
            multiTableSearchForObligor.setAssignedTo(getObligorData.getAssignedTo());
            multiTableSearchForObligor.setCurrentStatus(getObligorData.getTaskStatus());
            multiTableSearchForObligor.setRole(getObligorData.getRole());
            multiTableSearchForObligor.setChildReviewId(getObligorData.getChildReviewId());
            combinedList.add(multiTableSearchForObligor);
        }

        return combinedList;
    }


//    public List<MultiTableSearch> getGroupTasks(String role) {
//
//        String querydetailsSql = "SELECT * FROM querydetails WHERE (assignedTo IS NULL OR assignedTo = '') AND ROLE = ?";
//
//        String obligorSql = """
//        SELECT *
//        FROM OBLIGOR
//        WHERE assignedTo = ''
//          AND ROLE = ?
//          AND reviewStatus != 'FieldWorkCompleted';
//    """;
//
//        // Arguments for the queries
//        Object[] queryArgs = {role};
//
//        // Fetch data from querydetails
//        List<QueryDetails> queryDetails = jdbcTemplate.query(querydetailsSql, BeanPropertyRowMapper.newInstance(QueryDetails.class), queryArgs);
//        log.info("Query Details Retrieved: {}", queryDetails);
//
//        // Fetch data from obligor
//        List<Obligor> obligorDetails = jdbcTemplate.query(obligorSql, BeanPropertyRowMapper.newInstance(Obligor.class), queryArgs);
//        log.info("Obligor Details Retrieved: {}", obligorDetails);
//
//        // Combine results
//        List<MultiTableSearch> combinedData = getCBTask(queryDetails, obligorDetails);
//        log.info("Combined MultiTableSearch Data: {}", combinedData);
//
//        return combinedData;
//    }

    public List<MultiTableSearch> getGroupTasks(String role) {
        // Parse the role parameter into a list of roles
        List<String> roles = Arrays.asList(role.split(","));

        // Construct SQL queries with IN clause for roles
        String querydetailsSql = """
        SELECT *
        FROM querydetails
        WHERE (assignedTo IS NULL OR assignedTo = '')
          AND ROLE IN (:roles)
    """;

        String obligorSql = """
        SELECT *
        FROM OBLIGOR
        WHERE assignedTo = ''
          AND ROLE IN (:roles)
          AND reviewStatus != 'FieldWorkCompleted';
    """;

        // Use NamedParameterJdbcTemplate for dynamic parameters
        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

        // Create parameter map
        Map<String, Object> queryParams = Map.of("roles", roles);

        // Fetch data from querydetails
        List<QueryDetails> queryDetails = namedJdbcTemplate.query(querydetailsSql, queryParams, BeanPropertyRowMapper.newInstance(QueryDetails.class));
        log.info("Query Details Retrieved: {}", queryDetails);

        // Fetch data from obligor
        List<Obligor> obligorDetails = namedJdbcTemplate.query(obligorSql, queryParams, BeanPropertyRowMapper.newInstance(Obligor.class));
        log.info("Obligor Details Retrieved: {}", obligorDetails);

        // Combine results
        List<MultiTableSearch> combinedData = getCBTask(queryDetails, obligorDetails);
        log.info("Combined MultiTableSearch Data: {}", combinedData);

        return combinedData;
    }



    private List<MultiTableSearch> getCBTask(List<QueryDetails> queryDetailsRecords, List<Obligor> obligorRecords) {
        List<MultiTableSearch> combinedList = new ArrayList<>();


        for (int i = 0; i < queryDetailsRecords.size(); i++) {
            MultiTableSearch multiTableSearchForQuery = new MultiTableSearch();
            QueryDetails getQueryData = queryDetailsRecords.get(i);
            multiTableSearchForQuery.setReviewId(getQueryData.getReviewId());
            multiTableSearchForQuery.setDivision(getQueryData.getDivision());
            multiTableSearchForQuery.setGroupName(getQueryData.getGroupName());
            multiTableSearchForQuery.setAssignedTo(getQueryData.getAssignedTo());
            multiTableSearchForQuery.setRole(getQueryData.getRole());
            multiTableSearchForQuery.setCurrentStatus(getQueryData.getCurrentStatus());
            multiTableSearchForQuery.setCreatedOn((Timestamp) getQueryData.getCreatedDate());
            multiTableSearchForQuery.setCreatedBy(getQueryData.getCreatedBy());
            multiTableSearchForQuery.setChildReviewId("");
            combinedList.add(multiTableSearchForQuery);
        }
        for (int p = 0; p < obligorRecords.size(); p++) {
            MultiTableSearch multiTableSearchForObligor = new MultiTableSearch();
            Obligor getObligorData = obligorRecords.get(p);
            multiTableSearchForObligor.setReviewId(getObligorData.getReviewId());
            multiTableSearchForObligor.setGroupName(getObligorData.getGroupName());
            multiTableSearchForObligor.setCreatedBy(getObligorData.getCreatedBy());
            multiTableSearchForObligor.setCreatedOn(getObligorData.getCreatedOn());
            multiTableSearchForObligor.setDivision(getObligorData.getDivision());
            multiTableSearchForObligor.setAssignedTo(getObligorData.getAssignedTo());
            multiTableSearchForObligor.setCurrentStatus(getObligorData.getTaskStatus());
            multiTableSearchForObligor.setRole(getObligorData.getRole());
            multiTableSearchForObligor.setChildReviewId(getObligorData.getChildReviewId());
            combinedList.add(multiTableSearchForObligor);
        }

        return combinedList;
    }
}
