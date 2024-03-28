package com.ppc.identity.controller;

import com.ppc.identity.dto.AuthRequest;
import com.ppc.identity.entity.UserInfo;
import com.ppc.identity.serviceImpl.AuthServiceImpl;
import com.stoyanr.evictor.queue.PriorityEvictionQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()) {

            return this.authService.generateToken(authRequest.getUsername());
        }else {
            throw new RuntimeException("user not found");
        }
    }



    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        this.authService.validateToken(token);
        return "token is valid";
    }

    @PostMapping("/create-user")
    public String createUser(@RequestBody UserInfo userInfo) {
        authService.createUser(userInfo);
        return "user is created";
    }
}