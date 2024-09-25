package com.purna.controller;

import com.purna.dto.AuthRequest;
import com.purna.entity.User;
import com.purna.serviceImpl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()){
                return ResponseEntity.ok(authService.generateToken(authRequest.getUsername()));
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body("Authentication failed");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("Error :"+e.getMessage());
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token){
        this.authService.validateToken(token);
        return "token is valid";
    }

    @PostMapping("/createUser")
    ResponseEntity<Map<String,Object>> createUser(@RequestBody User user){
        Map<String,Object> response = new HashMap<>();
        User saveUser = authService.createUser(user);
        response.put("status",HttpStatus.CREATED.value());
        response.put("message","User Created Successfully..!");
        return ResponseEntity.ok().body(response);
    }
}
