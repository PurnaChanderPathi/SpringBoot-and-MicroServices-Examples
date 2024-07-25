package com.purna.controller;

import com.purna.dto.UserDto;
import com.purna.service.JwtServiceImpl;
import com.purna.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class LoginController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    Map<String,Object> map = new HashMap<>();

        @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody UserDto userDto){
        try {

            this.authenticationManager(userDto.getUsername(),userDto.getPassword());

        }catch (Exception e){
            throw new UsernameNotFoundException("Invalid Email Or Password");
        }
        this.authenticationManager(userDto.getUsername(),userDto.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDto.getUsername());
        String token = this.jwtService.generateToken(userDetails.getUsername());
        map.put("token",token);
        map.put("token expiration time", this.jwtService.getExpirationDateFromToken(token).toString());
        map.put("token expiration time in milli seconds", this.jwtService.getExpirationDateFromToken(token).getTime());
        map.put("message", "success");
        map.put("status", HttpStatus.OK.value());
        map.put("username", userDetails.getUsername());
        map.put("user-role", userDetails.getAuthorities().stream().map(auth -> auth.getAuthority()));
        return ResponseEntity.ok().body(map);
    }

    private void authenticationManager(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        }catch (DisabledException e){
            throw new DisabledException("User is Disabled");
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    private void forgotPassword(@PathVariable String username){

    }
}
