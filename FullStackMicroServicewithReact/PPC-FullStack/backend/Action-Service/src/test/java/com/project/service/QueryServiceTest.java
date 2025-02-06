package com.project.service;

import com.project.entity.QueryDetails;
import com.project.repository.QueryRepository;
import org.junit.jupiter.api.Assertions;
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
    QueryRepository queryRepository;

    @InjectMocks
    QueryService queryService;

    @Test
    void addQueryDetails(){
        System.out.println("my first unit test");
        QueryDetails queryDetails = new QueryDetails();
        queryDetails.setReviewId("PS3343");
        queryDetails.setCreatedBy("Pathi");

        String expectedQueryString = queryDetails.toString();

        Mockito.when(queryRepository.save(queryDetails)).thenReturn(expectedQueryString);
        String addedQuery = queryService.saveQueryDetails(queryDetails);

        Assertions.assertEquals(expectedQueryString,addedQuery,"The saved query details string does not match the expected string");

    }

}