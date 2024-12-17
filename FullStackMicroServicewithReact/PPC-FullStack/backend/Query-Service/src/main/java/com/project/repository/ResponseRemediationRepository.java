package com.project.repository;

import com.project.entity.ResponseQueryDetails;
import com.project.entity.ResponseRemediation;

import java.util.List;

public interface ResponseRemediationRepository {

    public ResponseRemediation findByChildReviewId(String childReviewId);

    public List<ResponseQueryDetails>  findResponseQueryByChildReviewId(String childReviewId);
}
