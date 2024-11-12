//package com.project.controller;
//
//import com.project.entity.QueryDetails;
//import com.project.service.QueryDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.List;
//
//@CrossOrigin
//@RestController("/multiSearch")
//public class QueryDetailsController {
//
//    @Autowired
//    private QueryDetailsService queryDetailsService;
//
//    @GetMapping("/query-details")
//    public ResponseEntity<List<QueryDetails>> getQueryDetails(
//            @RequestParam(required = false) String childReviewId,
//            @RequestParam(required = false) String reviewId) {
//        List<QueryDetails> queryDetails = queryDetailsService.getQueryDetails(childReviewId, reviewId);
//        return ResponseEntity.ok(queryDetails);
//    }
//
//    @GetMapping("/search")
//    public List<QueryDetails> fetchQueryDetails(
//            @RequestParam(value = "groupName", required = false) String groupName,
//            @RequestParam(value = "division", required = false) String division,
//            @RequestParam(value = "reviewId", required = false) String reviewId,
//            @RequestParam(value = "fromDate", required = false) String fromDate,
//            @RequestParam(value = "toDate", required = false) String toDate) {
//
//        // Convert String to LocalDate (yyyy-MM-dd format)
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate from = (fromDate != null) ? LocalDate.parse(fromDate, formatter) : null;
//        LocalDate to = (toDate != null) ? LocalDate.parse(toDate, formatter) : null;
//
//        // Fetch query details from service
//        return queryDetailsService.fetchQueryDetails(groupName, division, reviewId, from, to);
//    }
//}
