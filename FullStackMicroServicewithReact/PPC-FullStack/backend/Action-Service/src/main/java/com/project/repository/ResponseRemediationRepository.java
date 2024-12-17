package com.project.repository;

import com.project.entity.ResponseQueryDetails;
import com.project.entity.ResponseRemediation;

public interface ResponseRemediationRepository {

    void saveResponse(ResponseRemediation responseRemediation);

    void deleteResponse(String childReviewId);

    void saveResponseQuery(ResponseQueryDetails responseQueryDetails);

    void deleteResponseQuery(String querySequence);
}
