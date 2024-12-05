package com.project.service;

import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;
import com.project.repository.ObligorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObligorService {

    @Autowired
    private ObligorRepository obligorRepository;

    public List<Obligor> getObligorDetails(String reviewId){
        return obligorRepository.getObligorDetailsByReviewId(reviewId);
    }

    public List<ObligorDocument> getObligorDocumentDetails(String reviewId){
        return obligorRepository.getObligorDocumentByReviewId(reviewId);
    }
}
