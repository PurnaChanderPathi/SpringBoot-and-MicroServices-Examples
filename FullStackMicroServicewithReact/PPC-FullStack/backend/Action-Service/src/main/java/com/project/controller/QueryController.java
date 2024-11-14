package com.project.controller;

import com.project.entity.QueryDetails;
import com.project.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/action")
public class QueryController {

    @Autowired
    private QueryService queryService;

//    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String createQuery(@RequestBody QueryDetails queryDetails){
//        return queryService.saveQueryDetails(queryDetails);
//    }

    @PostMapping("/save")
    public Map<String,Object> createQuery(@RequestBody QueryDetails queryDetails){
        Map<String,Object> response = new HashMap<>();
        String savedData = queryService.saveQueryDetails(queryDetails);
        response.put("status", HttpStatus.OK.value());
        response.put("message","ReviewId Successfully Created");
        return response;
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
