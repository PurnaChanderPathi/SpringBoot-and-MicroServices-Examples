package com.purna.service;

import com.purna.entity.PostDetails;
import com.purna.repositories.PostDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String REPOSITORY_MICROSERVICE_URL = "http://localhost:8082/repository/post";

    public PostDetails savePost(PostDetails postDetails){
        PostDetails savePost = restTemplate.postForObject(REPOSITORY_MICROSERVICE_URL, postDetails, PostDetails.class);
        return savePost;
    }
}
