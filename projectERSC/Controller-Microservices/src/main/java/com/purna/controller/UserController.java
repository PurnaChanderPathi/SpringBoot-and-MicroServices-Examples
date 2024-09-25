package com.purna.controller;

import com.purna.entity.User;
import com.purna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userDetailsService;

    @PostMapping("/SavedUser")
    public ResponseEntity<User> savedUser(@RequestBody User userDetails){
        User savedUser = userDetailsService.savedUser(userDetails);
        return ResponseEntity.ok().body(savedUser);
    }
}
