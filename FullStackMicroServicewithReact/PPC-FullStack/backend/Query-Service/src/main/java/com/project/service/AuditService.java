package com.project.service;

import com.project.entity.AuditTrail;
import com.project.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public List<AuditTrail> getAllAuditByReviewId(String reviewId){
        return auditRepository.getAuditByReviewId(reviewId);
    }
}
