package com.project.controller;

import com.project.entity.QueryDetails;
import com.project.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping("/generateReviewId")
    public String generateReviewId(){
        return queryService.generateReviewId();
    }

    @GetMapping("/{reviewId}")
    public QueryDetails findByReviewId(@PathVariable String reviewId){
        return queryService.findByReviewId(reviewId);
    }

    @GetMapping("getAll")
    public List<QueryDetails> getAll(){
        return queryService.findAll();
    }
}
