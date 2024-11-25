package com.project.repository;

import com.project.entity.QueryDetails;

import java.util.List;

public interface QueryRepository {
    String generateReviewId();
    QueryDetails findByReviewId(String reviewId);
    List<QueryDetails> findAll();
//    List<QueryDetails> findByRoleAndCreatedBy(List<String> roles, String createdBy, String assignedTo);
}
