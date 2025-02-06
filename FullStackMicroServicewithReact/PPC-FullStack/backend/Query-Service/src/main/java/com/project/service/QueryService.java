package com.project.service;

import com.project.entity.Obligor;
import com.project.entity.QueryDetails;
import com.project.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public QueryDetails findByReviewId(String reviewId){
        return queryRepository.findByReviewId(reviewId);
    }


    public List<QueryDetails> findAll(){
        return queryRepository.findAll();
    }

    public String generateReviewId(){
        return queryRepository.generateReviewId();
    }

    public List<QueryDetails> findByRoleAndCreatedBy(List<String> roles, String assignedTo) {
        return queryRepository.findByRoleAndAssignedTo(roles, assignedTo);
    }

//    public List<QueryDetails> findByRoleAndCreatedBy(List<String> roles,String assignedTo) {
//        StringBuilder query = new StringBuilder("SELECT * " +
//
//                "FROM querydetails " +
//                "WHERE " +
//                "    (ROLE IS NULL OR TRIM(ROLE) IN (");
//
//        for (int i = 0; i < roles.size(); i++) {
//            query.append("?");
//            if (i < roles.size() - 1) {
//                query.append(", ");
//            }
//        }
//        query.append(")) " +
//                "AND (assignedTo IS NULL OR TRIM(assignedTo) = ?)");
//
//        Object[] args = new Object[roles.size() + 1];
//
//        for (int i = 0; i < roles.size(); i++) {
//            args[i] = roles.get(i);
//        }
//
//        args[roles.size()] = assignedTo != null ? assignedTo : "";
//
//        System.out.println("Executing SQL Query: " + query.toString());
//
//        return jdbcTemplate.query(query.toString(), args, BeanPropertyRowMapper.newInstance(QueryDetails.class));
//    }

    public List<QueryDetails> findByRoleAndAssignedTo(List<String> roles, String assignedTo) {
        // Ensure roles are valid
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }

        StringBuilder query = new StringBuilder("SELECT * FROM querydetails WHERE ");

        query.append("TRIM(ROLE) IS NOT NULL AND TRIM(ROLE) <> '' ");
        query.append("AND TRIM(ROLE) IN (");

        for (int i = 0; i < roles.size(); i++) {
            query.append("?");
            if (i < roles.size() - 1) {
                query.append(", ");
            }
        }
        query.append(") ");

        if (assignedTo != null && !assignedTo.trim().isEmpty()) {
            query.append("AND TRIM(assignedTo) IS NOT NULL AND TRIM(assignedTo) <> '' ");
            query.append("AND TRIM(assignedTo) = ? ");
        } else {
            query.append("AND TRIM(assignedTo) IS NOT NULL AND TRIM(assignedTo) <> '' ");
        }

        Object[] args = new Object[roles.size() + (assignedTo != null && !assignedTo.trim().isEmpty() ? 1 : 0)];

        for (int i = 0; i < roles.size(); i++) {
            args[i] = roles.get(i);
        }

        if (assignedTo != null && !assignedTo.trim().isEmpty()) {
            args[roles.size()] = assignedTo;
        }

        System.out.println("Executing SQL Query: " + query.toString());

        return jdbcTemplate.query(query.toString(), args, BeanPropertyRowMapper.newInstance(QueryDetails.class));
    }


}
