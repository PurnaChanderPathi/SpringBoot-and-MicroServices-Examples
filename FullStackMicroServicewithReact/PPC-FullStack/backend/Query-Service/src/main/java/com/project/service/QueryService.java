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

    public String saveQueryDetails(QueryDetails queryDetails){
        return queryRepository.save(queryDetails);
    }

    public QueryDetails saveQuery(QueryDetails queryDetails){
        return queryRepository.saveQuery(queryDetails);
    }

    public int updateQueryDetails(QueryDetails queryDetails){
        return queryRepository.update(queryDetails);
    }

    public QueryDetails updateQuery(QueryDetails queryDetails){
        return queryRepository.updateQuery(queryDetails);
    }

    public QueryDetails findByReviewId(Long reviewId){
        return queryRepository.findByReviewId(reviewId);
    }

    public int deleteByReviewId (Long reviewId){
        return queryRepository.deleteByReviewId(reviewId);
    }

    public List<QueryDetails> findAll(){
        return queryRepository.findAll();
    }

}
