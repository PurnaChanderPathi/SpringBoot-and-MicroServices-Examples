package com.purna.serviceImpl;

import com.purna.entity.UserInfo;
import com.purna.repository.UserInfoRepository;
import com.purna.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserInfo> getAll() {
        return userInfoRepository.findAll();
    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }

//    public String generateToken(String username) {
//        Optional<UserInfo> user = userInfoRepository.findByName(username);
//        if (user != null) {
//            // Logic to generate token
//            return "some-token";
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

    public UserInfo createUser(UserInfo userInfo){
        String password = userInfo.getPassword();
        String encoded = passwordEncoder.encode(password);
        userInfo.setPassword(encoded);
        userInfoRepository.save(userInfo);
        return userInfo;
    }
}
