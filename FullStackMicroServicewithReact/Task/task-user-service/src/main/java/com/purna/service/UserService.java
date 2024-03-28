package com.purna.service;

import com.purna.model.User;

import java.util.List;

public interface UserService {

    public User getUserProfile(String jwt);

    public List<User> getAllUsers();
}
