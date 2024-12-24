package com.project.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Dto.webRes;
import com.project.Dto.webobligor;
import com.project.entity.ResponseQueryDetails;
import com.project.entity.ResponseRemediation;
import com.project.repository.ResponseRemediationRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ResponseRemediationService {

    @Autowired
    private WebClient.Builder builder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResponseRemediationRepository responseRemediationRepository;

    public Map<String,Object> saveResponseRemediation(ResponseRemediation responseRemediation){
        Map<String, Object> response = new HashMap<>();
        webRes fetchedDetails = null;
        String findByChildReviewId = "http://localhost:9193/api/QueryObligor/findByChildReviewIdOfResponse/"+responseRemediation.getChildReviewId();

        try{
            String responseBody = builder.build().get()
                    .uri(findByChildReviewId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
             fetchedDetails = objectMapper.readValue(responseBody, webRes.class);
            log.info("fetchedDetails of Response & Remediation : {}",fetchedDetails);
        }catch (WebClientResponseException e){
            if(e.getCause() instanceof java.net.ConnectException){
                log.error("Error Fetching Response & Remediation : Service is offline",e);
            }else {
                log.error("Error Fetching Response & Remediation with ChildReviewId : {}",responseRemediation.getChildReviewId(),e);
            }
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        assert fetchedDetails != null;
        if(fetchedDetails.getResult() != null && fetchedDetails.getResult().getChildReviewId() != null ){
                response.put("status", HttpStatus.FOUND.value());
                response.put("message","Details Already there in Response & Remediation Stage with childId"+responseRemediation.getChildReviewId());
                log.info("Details Already there in Response & Remediation Stage : {} ",responseRemediation.getChildReviewId());
        }else{
            webobligor fetchedDetailofObligor = null;
            String findByChildReviewIdofObligor = "http://localhost:9193/api/QueryObligor/findByChildReviewId/"+responseRemediation.getChildReviewId();
            log.info("findByChildReviewIdofObligor : {}",findByChildReviewIdofObligor);

            try{
                String responseBody = builder.build().get()
                        .uri(findByChildReviewIdofObligor)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                ObjectMapper objectMapper = new ObjectMapper();
                fetchedDetailofObligor = objectMapper.readValue(responseBody, webobligor.class);
                log.info("fetchedDetailofObligor of Obligor : {}",fetchedDetailofObligor);
            }catch (WebClientResponseException e){
                if(e.getCause() instanceof java.net.ConnectException){
                    log.error("Error Fetching Obligor : Service is offline",e);
                }else {
                    log.error("Error Fetching Obligor with ChildReviewId : {}",responseRemediation.getChildReviewId(),e);
                }
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            assert fetchedDetailofObligor != null;
            responseRemediation.setReviewId(fetchedDetailofObligor.getResult().getReviewId());
            responseRemediation.setObligorName(fetchedDetailofObligor.getResult().getObligorName());
            responseRemediation.setObligorCifId(fetchedDetailofObligor.getResult().getObligorCifId());
            responseRemediation.setObligorPremId(fetchedDetailofObligor.getResult().getObligorPremId());
            responseRemediation.setGroupName(fetchedDetailofObligor.getResult().getGroupName());
            responseRemediation.setCreatedBy(fetchedDetailofObligor.getResult().getCreatedBy());
            responseRemediation.setCreatedOn(fetchedDetailofObligor.getResult().getCreatedOn());
            responseRemediation.setReviewStatus(fetchedDetailofObligor.getResult().getReviewStatus());
            responseRemediation.setChildReviewId(fetchedDetailofObligor.getResult().getChildReviewId());
            responseRemediation.setDivision(fetchedDetailofObligor.getResult().getDivision());
            responseRemediation.setIsActive(fetchedDetailofObligor.getResult().getIsActive());
            responseRemediation.setObservation(fetchedDetailofObligor.getResult().getObservation());
            responseRemediation.setTaskStatus(fetchedDetailofObligor.getResult().getTaskStatus());
            responseRemediation.setActivityLevel(fetchedDetailofObligor.getResult().getRole());

            responseRemediationRepository.saveResponse(responseRemediation);
            response.put("status", HttpStatus.OK.value());
            response.put("message","Response & Remediation Details Saved Successfully");
            log.info("Response & Remediation Details Saved Successfully");
        }
        return response;
    }

    public void deleteResponse(String childReviewId){
        responseRemediationRepository.deleteResponse(childReviewId);
    }

    public String generateChildReviewIdForResponseQuery(String childReviewId) {
        String query = "SELECT querysequence FROM responsequery WHERE childReviewId = ? ORDER BY resQueryId DESC";
        List<String> result = jdbcTemplate.queryForList(query, String.class,childReviewId);
        if(result.isEmpty()){
            return childReviewId + "_1";
        }
        log.info("lastChildReviewId : {}",result);
        String lastChildReviewId = result.get(0);
        log.info("lastChildReviewId get result at get(0) : {}",lastChildReviewId);

        String[] parts = lastChildReviewId.split("_");
        log.info("Splitting the last child review ID: {}", Arrays.toString(parts));

//        if (parts.length < 4) {
//            throw new IllegalArgumentException("Invalid child review ID format.");
//        }
        try {
            int lastChildId = Integer.parseInt(parts[parts.length - 1]);
            log.info("Extracted last child ID: {}", lastChildId);

            int newChildId = lastChildId + 1;
            log.info("New child ID (after adding 1): {}", newChildId);

            return String.join("_", Arrays.copyOf(parts, parts.length - 1)) + "_" + newChildId;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in child review ID: " + lastChildReviewId, e);
        }
    }

    public void saveResponseQuery(ResponseQueryDetails responseQueryDetails) {
        Timestamp tm = new Timestamp(System.currentTimeMillis());
        responseQueryDetails.setCreatedOn(tm);

        responseRemediationRepository.saveResponseQuery(responseQueryDetails);
    }

    public void deleteResponseQuery(String querySequence){
        responseRemediationRepository.deleteResponseQuery(querySequence);
    }
}
