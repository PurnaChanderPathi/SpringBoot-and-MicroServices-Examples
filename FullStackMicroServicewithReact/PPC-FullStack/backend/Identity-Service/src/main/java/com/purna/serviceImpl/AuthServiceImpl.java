package com.purna.serviceImpl;

import com.purna.entity.UserInfo;
import com.purna.repository.UserInfoRepository;
import com.purna.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
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

    @Override
    public Optional<UserInfo> findByName(String Name) {
        return userInfoRepository.findByName(Name);
    }


//    @Override
//    public List<String> getUsersWithCreditReviewerRole() {
//        List<String> usersWithCreditReviewer = new ArrayList<>();
//
//        List<UserInfo> userInfoList = userInfoRepository.findAll();
//        log.info("Users : {}",userInfoList);
//
//        for (UserInfo userInfo : userInfoList) {
//            String roles = Arrays.toString(userInfo.getRoles());
//            log.info("roles : {}",roles);
//
//            if (roles != null && !roles.isEmpty()) {
//                String[] roleArray = roles.split(",");
//                log.info("roleArray after cama separated : {}",roleArray);
//                for (String role : roleArray) {
//                    if (role.trim().equalsIgnoreCase("CreditReviewer")) {
//
//                        usersWithCreditReviewer.add(userInfo.getName());
//                        break;
//                    }
//                }
//            }
//        }
//
//        return usersWithCreditReviewer;
//    }

    public List<String> getUsersWithCreditReviewerRole() {
        List<String> usersWithCreditReviewer = new ArrayList<>();

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        log.info("Users retrieved: {}", userInfoList);

        for (UserInfo userInfo : userInfoList) {
            String roles = Arrays.toString(userInfo.getRoles());
            log.info("Raw roles for user {}: {}", userInfo.getName(), roles);

            if (roles != null && !roles.isEmpty()) {
                roles = roles.replaceAll("[\\[\\]]", "").trim();
                String[] roleArray = roles.split("\\s*,\\s*");
                log.info("Split roles array for user {}: {}", userInfo.getName(), Arrays.toString(roleArray));
                for (String role : roleArray) {
                    log.info("Checking role: '{}' for user: {}", role.trim(), userInfo.getName());
                    if (role.trim().equalsIgnoreCase("CreditReviewer")) {
                        usersWithCreditReviewer.add(userInfo.getName());
                        break;
                    }
                }
            }
        }

        return usersWithCreditReviewer;
    }




    public String generateToken(String userName){
        return jwtService.generateRefreshToken(userName);
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
