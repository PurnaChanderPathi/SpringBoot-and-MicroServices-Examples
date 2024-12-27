package com.project.service;

import com.project.entity.AuditTrail;
import com.project.repository.AuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuditTrailService {

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    public String saveAuditTrial(AuditTrail auditTrail){
        Timestamp inTime = new Timestamp(System.currentTimeMillis());
        auditTrail.setInTime(inTime);
        return auditTrailRepository.saveAudit(auditTrail);
    }

    public AuditTrail updateAuditTrail(AuditTrail auditTrail){
        Timestamp outTime = new Timestamp(System.currentTimeMillis());
        auditTrail.setOutTime(outTime);
        return auditTrailRepository.update(auditTrail);
    }
}
