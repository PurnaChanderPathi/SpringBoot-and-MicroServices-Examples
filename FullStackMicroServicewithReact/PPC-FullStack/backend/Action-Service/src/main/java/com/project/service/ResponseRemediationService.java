package com.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Dto.webRes;
import com.project.entity.ResponseRemediation;
import com.project.repository.ResponseRemediationRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ResponseRemediationService {

    @Autowired
    private WebClient.Builder builder;

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

        if(fetchedDetails.getResult() != null && fetchedDetails.getResult().getChildReviewId() != null ){
                response.put("status", HttpStatus.FOUND.value());
                response.put("message","Details Already there in Response & Remediation Stage with childId"+responseRemediation.getChildReviewId());
                log.info("Details Already there in Response & Remediation Stage : {} ",responseRemediation.getChildReviewId());
        }else{
            responseRemediationRepository.saveResponse(responseRemediation);
            response.put("status", HttpStatus.OK.value());
            response.put("message","Response & Remediation Details Saved Successfully");
            log.info("Response & Remediation Details Saved Successfully");
        }
        return response;
    }
}
