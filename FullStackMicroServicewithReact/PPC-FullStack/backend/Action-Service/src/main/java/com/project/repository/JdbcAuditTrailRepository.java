package com.project.repository;

import com.project.entity.AuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public AuditTrail update(AuditTrail auditTrail) {
    StringBuilder query = new StringBuilder("UPDATE AUDITTRAIL SET ");
        List<Object> args = new ArrayList<>();

        if(auditTrail.getCurrentAction() != null){
            query.append("currentAction = ?, ");
            args.add(auditTrail.getCurrentAction());
        }

        if(auditTrail.getInTime() != null){
            query.append("inTime = ?, ");
            args.add(auditTrail.getInTime());
        }

        if(auditTrail.getOutTime() != null){
            query.append("outTime = ?, ");
            args.add(auditTrail.getOutTime());
        }
        if(auditTrail.getReviewId()!= null){
            query.append("reviewId = ?, ");
            args.add(auditTrail.getActionedBy());
        }
        query.setLength(query.length() - 2); // Remove trailing ", "

        query.append(" Where actionedBy = ?");
        args.add(auditTrail.getReviewId());

        int result = jdbcTemplate.update(query.toString(),args.toArray());
        if(result > 0){
            return auditTrail;
        }else {
            throw new RuntimeException("Update failed for Audit trail");
        }
    }
}
