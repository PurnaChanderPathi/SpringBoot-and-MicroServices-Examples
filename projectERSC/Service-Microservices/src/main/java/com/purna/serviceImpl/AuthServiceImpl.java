package com.purna.serviceImpl;

import com.purna.entity.User;
import com.purna.repositories.UserDetailsRepository;
import com.purna.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userDetailsRepository.findAll();
    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

    public User createUser(User user){
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        return userDetailsRepository.save(user);
    }
}
