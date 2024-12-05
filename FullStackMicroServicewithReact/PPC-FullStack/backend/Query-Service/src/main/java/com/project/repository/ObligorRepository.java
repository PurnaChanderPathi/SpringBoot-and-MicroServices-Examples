package com.project.repository;

import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;

import java.util.List;

public interface ObligorRepository {

    List<Obligor> getObligorDetailsByReviewId(String reviewid);

    List<ObligorDocument> getObligorDocumentByReviewId(String reviewId);
}
