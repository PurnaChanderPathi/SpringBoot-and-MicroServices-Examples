package com.purna.controller;

import com.purna.dto.AuthRequest;
import com.purna.entity.UserInfo;
import com.purna.serviceImpl.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private AuthenticationManager authenticationManager;

//    @PostMapping("/token")
//    public String getToken(@RequestBody AuthRequest authRequest){
//
//        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
//        if(authenticate.isAuthenticated()){
//            return this.authService.generateToken(authRequest.getUsername());
//        }else{
//            throw new RuntimeException("user not found");
//        }
//    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                return ResponseEntity.ok(authService.generateToken(authRequest.getUsername()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        this.authService.validateToken(token);
        return "token is valid";
    }

    @PostMapping("/create-user")
    public String createUser(@RequestBody UserInfo userInfo){
        authService.createUser(userInfo);
        return "user is created";
    }

    @PostMapping("/createUser")
    ResponseEntity<Map<String,Object>> createUsers(@RequestBody UserInfo userInfo){
        Map<String,Object> response = new HashMap<>();
        UserInfo savedUser = authService.createUser(userInfo);
        response.put("status", HttpStatus.CREATED.value());
        response.put("message","user created successfully");
        return ResponseEntity.ok().body(response);
    }

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
}
