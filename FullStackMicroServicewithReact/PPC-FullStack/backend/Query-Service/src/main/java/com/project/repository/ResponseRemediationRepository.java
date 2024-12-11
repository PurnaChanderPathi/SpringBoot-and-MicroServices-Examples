package com.project.repository;

import com.project.entity.ResponseRemediation;

public interface ResponseRemediationRepository {

    public ResponseRemediation findByChildReviewId(String childReviewId);
}
