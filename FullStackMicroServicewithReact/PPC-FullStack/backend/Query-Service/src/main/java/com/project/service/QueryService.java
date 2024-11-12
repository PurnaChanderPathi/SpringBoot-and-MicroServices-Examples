package com.project.service;

import com.project.entity.QueryDetails;
import com.project.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;



    public QueryDetails findByReviewId(String reviewId){
        return queryRepository.findByReviewId(reviewId);
    }


    public List<QueryDetails> findAll(){
        return queryRepository.findAll();
    }

    public String generateReviewId(){
        return queryRepository.generateReviewId();
    }

}
