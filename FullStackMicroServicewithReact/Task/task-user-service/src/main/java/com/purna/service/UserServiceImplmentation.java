package com.purna.service;

import com.purna.config.JwtProvider;
import com.purna.model.User;
import com.purna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplmentation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User getUserProfile(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}
