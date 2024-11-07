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

    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createQuery(@RequestBody QueryDetails queryDetails){
        return queryService.saveQueryDetails(queryDetails);
    }

    @PostMapping("/saveQuery")
    public QueryDetails createQueryDetails(@RequestBody QueryDetails queryDetails){
        return queryService.saveQuery(queryDetails);
    }

    @PutMapping("/{reviewId}")
    public int updateQuery(@PathVariable String reviewId,@RequestBody QueryDetails queryDetails){
        queryDetails.setReviewId(reviewId);
        return queryService.updateQueryDetails(queryDetails);
    }

    @PutMapping()
    public QueryDetails updateQuery(@RequestBody QueryDetails queryDetails){

        return queryService.updateQuery(queryDetails);
    }

    @GetMapping("/{reviewId}")
    public QueryDetails findByReviewId(@PathVariable Long reviewId){
        return queryService.findByReviewId(reviewId);
    }

    @DeleteMapping("/{reviewId}")
    public int deleteQuery(@PathVariable Long reviewId){
        return queryService.deleteByReviewId(reviewId);
    }

    @GetMapping("getAll")
    public List<QueryDetails> getAll(){
        return queryService.findAll();
    }
}
