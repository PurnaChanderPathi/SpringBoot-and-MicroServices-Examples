package com.project.service;

import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;
import com.project.repository.ObligorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ObligorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObligorRepository obligorRepository;

    public String generateChildReviewId(String reviewId) {
        String query = "SELECT childReviewId FROM OBLIGOR WHERE reviewId = ? ORDER BY childReviewId DESC LIMIT 1";
        List<String> result = jdbcTemplate.queryForList(query, String.class,reviewId);
        if(result.isEmpty()){
            return reviewId + "_1";
        }
        String lastChildReviewId = result.get(0);
        int lastChildId = Integer.parseInt(lastChildReviewId.split("_")[1]);
        int newChildId = lastChildId + 1;
        return reviewId + "_" + newChildId;
    }

    public void saveObligor(Obligor obligor){
        obligorRepository.saveObligor(obligor);
    }

    public Obligor updateObligor(Obligor obligor){
        return obligorRepository.updateObligor(obligor);
    }

    public void deleteObligor(String obligorId){
        obligorRepository.deleteobligor(obligorId);
    }

    public ObligorDocument saveObligorDocument(ObligorDocument obligorDocument){
        Timestamp tm = new Timestamp(System.currentTimeMillis());
        obligorDocument.setUploadedOn(tm);
        obligorRepository.saveObligorDocument(obligorDocument);
        return obligorDocument;
    }

    public void deleteObligorDoc(String obligorDocId){
        obligorRepository.deleteObligorDocument(obligorDocId);
    }
}
