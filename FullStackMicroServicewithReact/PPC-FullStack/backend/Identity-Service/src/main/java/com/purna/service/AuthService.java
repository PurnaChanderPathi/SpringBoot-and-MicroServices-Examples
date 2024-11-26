package com.purna.service;

import com.purna.entity.UserInfo;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    public List<UserInfo> getAll();
    public Optional<UserInfo> findByName(String name);
    public List<String> getUsersWithCreditReviewerRole();
}
