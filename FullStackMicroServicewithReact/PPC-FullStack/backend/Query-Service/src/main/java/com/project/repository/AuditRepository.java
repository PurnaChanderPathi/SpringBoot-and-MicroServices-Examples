package com.project.repository;

import com.project.entity.AuditTrail;

import java.util.List;

public interface AuditRepository {

    List<AuditTrail> getAuditByReviewId(String reviewId);
}
