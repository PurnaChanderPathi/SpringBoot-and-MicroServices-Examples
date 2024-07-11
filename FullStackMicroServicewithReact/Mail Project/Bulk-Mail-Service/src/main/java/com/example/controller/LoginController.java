package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LoginDto;
import com.example.exception.UserNotFoundException;
import com.example.serviceImpl.JwtServiceImpl;

@RestController
@RequestMapping("api/v1/auth")
public class LoginController {

	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	private JwtServiceImpl service;
	
	@Autowired
	private AuthenticationManager authenticate; 
	
    Map<String, Object> map = new HashMap<>();
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginDto) throws Exception {
    	
    	try {
            this.authenticate(loginDto.getUsername(), loginDto.getPassword());
		} catch (Exception e) {
			throw new UserNotFoundException("Invalid username or password");
		}
        this.authenticate(loginDto.getUsername(), loginDto.getPassword());
        UserDetails userDetails = this.userService.loadUserByUsername(loginDto.getUsername());
        String token = this.service.generateToken(userDetails.getUsername());

        map.put("token", token);
        map.put("token expiration time", this.service.getExpirationDateFromToken(token).toString());
        map.put("token expiration time in milli seconds", this.service.getExpirationDateFromToken(token).getTime());
        map.put("message", "success");
        map.put("status", HttpStatus.OK.value());
        map.put("username", userDetails.getUsername());
        map.put("user-role", userDetails.getAuthorities().stream().map(auth -> auth.getAuthority()));

        return ResponseEntity.ok().body(map);

    }
    
	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticate.authenticate(authenticationToken);
		} catch (DisabledException e) {
			throw new DisabledException("user is disabled");
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("bad credentials");
		}		
	}
	
}
