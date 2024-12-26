package com.project.repository;

import com.project.Dto.ResponseQueryDto;
import com.project.entity.ResponseQueryDetails;
import com.project.entity.ResponseRemediation;

public interface ResponseRemediationRepository {

    void saveResponse(ResponseRemediation responseRemediation);

    void deleteResponse(String childReviewId);

    void saveResponseQuery(ResponseQueryDetails responseQueryDetails);

    ResponseQueryDto updateResponse(ResponseQueryDto responseQueryDto);

    void deleteResponseQuery(String querySequence);
}
