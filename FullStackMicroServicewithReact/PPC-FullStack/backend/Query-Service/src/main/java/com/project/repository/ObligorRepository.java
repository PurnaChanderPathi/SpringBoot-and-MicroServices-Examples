package com.project.repository;

import com.project.Dto.FileData;
import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;

import java.util.List;

public interface ObligorRepository {

    List<Obligor> getObligorDetailsByReviewId(String reviewid);

    List<ObligorDocument> getObligorDocumentByReviewId(String reviewId);


    ObligorDocument getObligorDocumentByobligorDocId(String obligorDocId);

    Obligor findByChildReviewId(String childReviewId);
}
