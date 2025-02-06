package com.project.service;

import com.project.entity.QueryDetails;
import com.project.repository.QueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QueryServiceTest {

    @Mock
    private QueryRepository queryRepository;

    @InjectMocks
    private QueryService queryService;

    private QueryDetails queryDetails;

    @BeforeEach
    void setUp(){
         queryDetails = new QueryDetails();
         queryDetails.setReviewId("PS");
         queryDetails.setDivision("Amlak");
         queryDetails.setGroupName("JAVA");
    }

    @Test
    void testFindByReviewId_Success() {
        String reviewId = "PS";

        Mockito.when(queryRepository.findByReviewId(reviewId)).thenReturn(queryDetails);

        QueryDetails result = queryService.findByReviewId(reviewId);

       assertNotNull(result);
       assertEquals("PS",result.getReviewId());

       Mockito.verify(queryRepository,Mockito.times(1)).findByReviewId(reviewId);
    }

    @Test
    void testGenerateReviewId() {
        String expectedReviewId =  "PPC-20250110-014";

        Mockito.when(queryRepository.generateReviewId()).thenReturn(expectedReviewId);
        String actualReviewId = queryService.generateReviewId();

        assertNotNull(actualReviewId);
        assertTrue(actualReviewId.startsWith("PPC"));
        assertTrue(actualReviewId.length() > 14);

        Mockito.verify(queryRepository,Mockito.times(1)).generateReviewId();
    }

}