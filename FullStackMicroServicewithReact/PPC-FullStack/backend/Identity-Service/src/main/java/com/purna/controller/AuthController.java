package com.purna.controller;

import com.purna.dto.AuthRequest;
import com.purna.entity.UserInfo;
import com.purna.serviceImpl.AuthServiceImpl;
import com.purna.serviceImpl.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Slf4j
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @PostMapping("/token")
//    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) {
//        try {
//            Authentication authenticate = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//            if (authenticate.isAuthenticated()) {
//                return ResponseEntity.ok(authService.generateToken(authRequest.getUsername()));
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> getToken(@RequestBody AuthRequest authRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            if (authenticate.isAuthenticated()) {
                String accessToken = jwtService.generateAccessToken(authRequest.getUsername());
                String refreshToken = jwtService.generateRefreshToken(authRequest.getUsername());
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Authentication successful");
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", HttpStatus.UNAUTHORIZED.value());
                response.put("message", "Authentication failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error: " + e.getMessage());
            log.error("Error occurred during authentication: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        try {
            jwtService.validateToken(token);
            return ResponseEntity.ok("Token is valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, Object>> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) {
        Map<String, Object> response = new HashMap<>();
        try {
            jwtService.validateToken(refreshToken);
            String userName = jwtService.getUsernameFromToken(refreshToken);
            String newAccessToken = jwtService.generateAccessToken(userName);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Token refreshed successfully");
            response.put("accessToken", newAccessToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", HttpStatus.UNAUTHORIZED.value());
            response.put("message", "Invalid or expired refresh token");
            log.error("Error during token refresh: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

//    @PostMapping("/create-user")
//    public String createUser(@RequestBody UserInfo userInfo){
//        authService.createUser(userInfo);
//        return "user is created";
//    }

    @PostMapping("/createUser")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserInfo userInfo) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserInfo savedUser = authService.createUser(userInfo);
            response.put("status", HttpStatus.CREATED.value());
            response.put("message", "User created successfully");
            response.put("user", savedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error: " + e.getMessage());
            log.error("Error occurred while creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    @PostMapping("/createUser")
//    ResponseEntity<Map<String,Object>> createUsers(@RequestBody UserInfo userInfo){
//        Map<String,Object> response = new HashMap<>();
//        UserInfo savedUser = authService.createUser(userInfo);
//        response.put("status", HttpStatus.CREATED.value());
//        response.put("message","user created successfully");
//        return ResponseEntity.ok().body(response);
//    }

    @GetMapping("getByName/{name}")
    ResponseEntity<Map<String,Object>> findByName(@PathVariable String name){
        log.info("Entered findByName");
        Map<String,Object> response = new HashMap<>();
        try {
            Optional<UserInfo> findDetails = authService.findByName(name);
            if(findDetails.isPresent()){
                response.put("status",HttpStatus.OK.value());
                response.put("message","User Details Fetched Successfully..!");
                response.put("result",findDetails);
                log.info("User Details Fetched Successfully");
                return new ResponseEntity<>(response,HttpStatus.OK);
            }else {
                response.put("status",HttpStatus.NOT_FOUND.value());
                response.put("message","User Details not found with UserName :"+name);
                log.warn("Copy string literal text to the clipboard");
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            log.info("Error occurred while fetching User Details with UserName :"+name);
            response.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message","Error occurred while fetching User Details with UserName :"+name);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserByCreditReviewerRole")
    public Map<String,Object> getUserByCreditReviewerRole(){
        Map<String,Object> response = new HashMap<>();
        List<String> result = authService.getUsersWithCreditReviewerRole();
        if(!result.isEmpty()){
            response.put("status",HttpStatus.OK.value());
            response.put("message","Users Fetched Successfully with CreditReviewer");
            response.put("result",result);
        }else {
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Users Failed to Fetched  with CreditReviewer");
        }
        return response;
    }
}
