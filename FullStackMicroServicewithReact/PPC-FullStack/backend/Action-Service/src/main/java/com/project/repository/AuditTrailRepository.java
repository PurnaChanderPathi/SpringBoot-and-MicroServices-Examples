package com.project.repository;

import com.project.entity.AuditTrail;

public interface AuditTrailRepository {

    String saveAudit(AuditTrail auditTrail);
}
