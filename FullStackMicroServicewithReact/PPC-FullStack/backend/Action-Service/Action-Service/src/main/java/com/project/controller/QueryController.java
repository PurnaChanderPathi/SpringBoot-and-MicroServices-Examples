package com.project.controller;

import com.project.entity.QueryDetails;
import com.project.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/action")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createQuery(@RequestBody QueryDetails queryDetails){
        return queryService.saveQueryDetails(queryDetails);
    }

    @PutMapping
    public QueryDetails updateQuery(@RequestBody QueryDetails queryDetails){

        return queryService.updateQuery(queryDetails);
    }

    @DeleteMapping("/{reviewId}")
    public String deleteQuery(@PathVariable String reviewId){
        return queryService.deleteByReviewId(reviewId);
    }
}
