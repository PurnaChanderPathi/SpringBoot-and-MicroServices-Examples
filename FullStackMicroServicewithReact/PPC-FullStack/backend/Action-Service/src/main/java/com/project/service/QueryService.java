package com.project.service;

import com.project.entity.QueryDetails;
import com.project.repository.QueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
public class QueryService {

    private static final Logger log = LoggerFactory.getLogger(QueryService.class);
    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private WebClient.Builder builder;


    public String saveQueryDetails(QueryDetails queryDetails){
       queryDetails.setRole("SrCreditReviewer");
       queryDetails.setCurrentStatus("Created");
        return queryRepository.save(queryDetails);
    }




    public QueryDetails updateQuery(QueryDetails queryDetails){
        QueryDetails details = new QueryDetails();
        String findByReviewId = "http://localhost:9193/api/query/"+queryDetails.getReviewId();
        try {
             details = builder.build().get()
                    .uri(findByReviewId)
                    .retrieve()
                    .bodyToMono(QueryDetails.class)
                    .block();
            log.info("QueryDetails : {}",details );
        } catch (WebClientResponseException e) {
            if (e.getCause() instanceof java.net.ConnectException) {
                log.error("Error fetching QueryService: Service is offline", e);
            } else {
                log.error("Error fetching QueryService reviewId {}", queryDetails.getReviewId(), e);
            }
        }
        if(details.getReviewId() == null || details.getReviewId().isEmpty()){
            throw new RuntimeException("QueryService not found with ReviewId :"+queryDetails.getReviewId());
        }else {
            if (queryDetails.getDivision() == null) {
                queryDetails.setDivision(details.getDivision());
            }
            if (queryDetails.getGroupName() == null) {
                queryDetails.setGroupName(details.getGroupName());
            }
//            if (queryDetails.getAssignedTo() == null) {
//                queryDetails.setAssignedTo(details.getAssignedTo());
//            }
            if (queryDetails.getRole() == null) {
                queryDetails.setRole(details.getRole());
            }
            if (queryDetails.getCurrentStatus() == null) {
                queryDetails.setCurrentStatus(details.getCurrentStatus());
            }
            if (queryDetails.getCreatedDate() == null) {
                queryDetails.setCreatedDate(details.getCreatedDate());
            }
            if (queryDetails.getCreatedBy() == null) {
                queryDetails.setCreatedBy(details.getCreatedBy());
            }
            if(queryDetails.getPlanning() == null){
                queryDetails.setPlanning(details.getPlanning());
            }
            if(queryDetails.getFieldwork() == null){
                queryDetails.setFieldwork(details.getFieldwork());
            }
            if(queryDetails.getAction() == null){
                queryDetails.setAction(details.getAction());
            }
            return queryRepository.updateQuery(queryDetails);
        }
    }

    public String deleteByReviewId (String reviewId){
        return queryRepository.deleteByReviewId(reviewId);
    }
}

