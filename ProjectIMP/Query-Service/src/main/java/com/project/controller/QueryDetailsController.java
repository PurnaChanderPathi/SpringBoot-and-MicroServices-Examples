package com.project.controller;

import com.project.entity.QueryDetails;
import com.project.service.QueryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryDetailsController {

    @Autowired
    private QueryDetailsService queryDetailsService;

    @GetMapping("/query-details")
    public ResponseEntity<List<QueryDetails>> getQueryDetails(
            @RequestParam(required = false) String childReviewId,
            @RequestParam(required = false) String reviewId) {
        List<QueryDetails> queryDetails = queryDetailsService.getQueryDetails(childReviewId, reviewId);
        return ResponseEntity.ok(queryDetails);
    }
}
