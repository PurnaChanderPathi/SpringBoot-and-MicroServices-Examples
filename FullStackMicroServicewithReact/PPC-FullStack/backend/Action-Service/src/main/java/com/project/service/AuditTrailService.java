package com.project.service;

import com.project.entity.AuditTrail;
import com.project.repository.AuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditTrailService {

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    public String saveAuditTrial(AuditTrail auditTrail){
        return auditTrailRepository.saveAudit(auditTrail);
    }
}
