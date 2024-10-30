package com.project.service;

import com.project.entity.QueryDetails;
import com.project.repository.QueryDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryDetailsService {

    @Autowired
    private QueryDetailsDao queryDetailsDao;

    public List<QueryDetails> getQueryDetails(String childReviewId, String reviewId) {
        return queryDetailsDao.getQueryDetails(childReviewId, reviewId);
    }
}
