package com.project.service;

import com.project.entity.Obligor;
import com.project.repository.ObligorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ObligorServiceTest {

    @Mock
    ObligorRepository obligorRepository;

    @InjectMocks
    ObligorService obligorService;

    @Test
    void AddObligor(){
        Obligor obligor = new Obligor();
        obligor.setReviewId("PPPS");
        obligor.setObligorName("PS");

        Mockito.doNothing().when(obligorRepository).saveObligor(Mockito.any(Obligor.class));

        obligorService.saveObligor(obligor);

        Mockito.verify(obligorRepository,Mockito.times(1)).saveObligor(Mockito.argThat(argument ->
                argument.getReviewId().equals("PPPS") &&
                        argument.getObligorName().equals("PS")));

        Assertions.assertNotNull(obligor.getCreatedOn(),"the CreatedOn field should be set by Service Layer");
    }
  
}