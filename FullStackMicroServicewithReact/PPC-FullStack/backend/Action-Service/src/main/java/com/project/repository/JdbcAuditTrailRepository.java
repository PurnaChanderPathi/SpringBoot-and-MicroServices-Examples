package com.project.repository;

import com.project.entity.AuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class JdbcAuditTrailRepository implements AuditTrailRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String saveAudit(AuditTrail auditTrail) {
        String query = "INSERT INTO AUDITTRAIL (reviewId,currentAction,inTime,outTime,actionedBy)VALUES(?,?,?,?,?)";
        Object [] args = {
                auditTrail.getReviewId(),
                auditTrail.getCurrentAction(),
                auditTrail.getInTime(),
                auditTrail.getOutTime(),
                auditTrail.getActionedBy()
        };
         jdbcTemplate.update(query,args);
         return "Audit Trial Inserted Successfully";
    }
}
