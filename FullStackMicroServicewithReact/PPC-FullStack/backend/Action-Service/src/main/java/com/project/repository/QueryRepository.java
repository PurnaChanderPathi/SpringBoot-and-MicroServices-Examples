package com.project.repository;

import com.project.entity.QueryDetails;

public interface QueryRepository {
    String save(QueryDetails queryDetails);
    QueryDetails updateQuery(QueryDetails queryDetails);
    String deleteByReviewId(String reviewId);
}
