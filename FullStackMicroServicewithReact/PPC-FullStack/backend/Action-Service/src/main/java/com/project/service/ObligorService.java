package com.project.service;

import com.project.entity.Obligor;
import com.project.repository.ObligorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObligorService {

    @Autowired
    private ObligorRepository obligorRepository;

    public void saveObligor(Obligor obligor){
        obligorRepository.saveObligor(obligor);
    }

    public void updateObligor(Obligor obligor){

    }
}
