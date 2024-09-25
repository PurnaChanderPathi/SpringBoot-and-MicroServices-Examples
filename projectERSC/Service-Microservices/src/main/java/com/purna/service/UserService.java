package com.purna.service;

import com.purna.entity.User;
import com.purna.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public User savedUser(User userDetails){
        User saveUser = new User();
        saveUser.setUsername(userDetails.getUsername());
        saveUser.setEmail(userDetails.getEmail());
        return userDetailsRepository.save(saveUser);
    }
}
