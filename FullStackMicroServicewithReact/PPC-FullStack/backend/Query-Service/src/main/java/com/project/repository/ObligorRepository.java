package com.project.repository;

import com.project.Dto.FileData;
import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;
import com.project.entity.ResponseRemediation;

import java.util.List;

public interface ObligorRepository {

    List<Obligor> getObligorDetailsByReviewId(String reviewid);

    List<ObligorDocument> getObligorDocumentByReviewId(String reviewId);

    List<ResponseRemediation> getResponseRemediationByReviewId(String reviewId);


    ObligorDocument getObligorDocumentByobligorDocId(String obligorDocId);

    Obligor findByChildReviewId(String childReviewId);

    List<Obligor> findByActivityLevel(String assignedTo);
}
