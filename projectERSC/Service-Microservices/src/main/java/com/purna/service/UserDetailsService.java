package com.purna.service;

import com.purna.entity.UserDetails;
import com.purna.repositories.PostDetailsRepository;
import com.purna.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String REPOSITORY_MICROSERVICE_URL = "http://localhost:8082/repository/user";

    public UserDetails saveUser(UserDetails userDetails){
        UserDetails savedUser = restTemplate.postForObject(REPOSITORY_MICROSERVICE_URL, userDetails, UserDetails.class);
        return savedUser;
    }

//    @Autowired
//    private UserDetailsRepository userDetailsRepository;
//
//    public UserDetails savedUser(UserDetails userDetails){
//        UserDetails saveUser = new UserDetails();
//        saveUser.setName(userDetails.getName());
//        saveUser.setEmail(userDetails.getEmail());
//        return userDetailsRepository.save(saveUser);
//    }
}
