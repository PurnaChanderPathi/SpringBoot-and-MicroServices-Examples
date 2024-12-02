package com.project.service;

import com.project.entity.QueryDetails;
import com.project.repository.QueryDetailsDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QueryDetailsService {

    @Autowired
    private QueryDetailsDao queryDetailsDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<QueryDetails> getQueryDetails( String reviewId) {
        return queryDetailsDao.getQueryDetails(reviewId);
    }

    public List<QueryDetails> fetchQueryDetails(String groupName, String division, String reviewId, LocalDate fromDate, LocalDate toDate) {
        // Start building the query
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM querydetails WHERE 1=1");

        // List to store parameters dynamically
        List<Object> params = new ArrayList<>();

        // Add condition for groupName if it's provided
        if (groupName != null && !groupName.isEmpty()) {
            queryBuilder.append(" AND groupName = ?");
            params.add(groupName);
        }

        // Add condition for division if it's provided
        if (division != null && !division.isEmpty()) {
            queryBuilder.append(" AND division = ?");
            params.add(division);
        }

        // Add condition for reviewId if it's provided
        if (reviewId != null && !reviewId.isEmpty()) {
            queryBuilder.append(" AND reviewId = ?");
            params.add(reviewId);
        }

        // Add condition for createdDate if fromDate and toDate are provided
        if (fromDate != null && toDate != null) {
            queryBuilder.append(" AND createdDate BETWEEN ? AND ?");
            params.add(java.sql.Date.valueOf(fromDate)); // Convert LocalDate to java.sql.Date
            params.add(java.sql.Date.valueOf(toDate));   // Convert LocalDate to java.sql.Date
        }
        // Add condition for createdDate if only fromDate is provided
        else if (fromDate != null) {
            queryBuilder.append(" AND createdDate >= ?");
            params.add(java.sql.Date.valueOf(fromDate)); // Convert LocalDate to java.sql.Date
        }
        // Add condition for createdDate if only toDate is provided
        else if (toDate != null) {
            queryBuilder.append(" AND createdDate <= ?");
            params.add(java.sql.Date.valueOf(toDate));   // Convert LocalDate to java.sql.Date
        }

        // Convert params list to an array for query execution
        Object[] paramArray = params.toArray(new Object[0]);

        // Execute the query using JdbcTemplate
        return jdbcTemplate.query(
                queryBuilder.toString(),
                paramArray,
                new BeanPropertyRowMapper<>(QueryDetails.class)
        );
    }
    }
