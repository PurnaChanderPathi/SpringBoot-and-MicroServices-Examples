package com.project.service;

import com.netflix.discovery.converters.Auto;
import com.project.entity.ResponseQueryDetails;
import com.project.entity.ResponseRemediation;
import com.project.repository.ResponseRemediationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseRemediationService {

    @Autowired
    private ResponseRemediationRepository responseRemediationRepository;

    public ResponseRemediation findByChildReviewId(String childReviewId){
        return responseRemediationRepository.findByChildReviewId(childReviewId);
    }

    public List<ResponseQueryDetails> findResponseQueryByChildReviewId(String childReviewId){
        return responseRemediationRepository.findResponseQueryByChildReviewId(childReviewId);
    }
}
