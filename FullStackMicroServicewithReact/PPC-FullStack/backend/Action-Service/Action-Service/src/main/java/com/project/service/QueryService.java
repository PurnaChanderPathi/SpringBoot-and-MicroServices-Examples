package com.project.service;

import com.project.entity.QueryDetails;
import com.project.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    public String saveQueryDetails(QueryDetails queryDetails){
        return queryRepository.save(queryDetails);
    }

    public String generateReviewId(){
        return queryRepository.generateReviewId();
    }

    public QueryDetails updateQuery(QueryDetails queryDetails){
        return queryRepository.updateQuery(queryDetails);
    }

    public String deleteByReviewId (String reviewId){
        return queryRepository.deleteByReviewId(reviewId);
    }
}
