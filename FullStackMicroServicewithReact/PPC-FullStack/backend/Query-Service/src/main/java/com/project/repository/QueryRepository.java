package com.project.repository;

import com.project.entity.QueryDetails;

import java.util.List;

public interface QueryRepository {
    String save(QueryDetails queryDetails);
    String generateReviewId();
    QueryDetails saveQuery(QueryDetails queryDetails);
    int update(QueryDetails queryDetails);
    QueryDetails updateQuery(QueryDetails queryDetails);
    QueryDetails findByReviewId(Long reviewId);
    int deleteByReviewId(Long reviewId);
    List<QueryDetails> findAll();
}
