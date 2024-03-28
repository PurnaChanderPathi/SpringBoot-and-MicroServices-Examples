package com.ppc.identity.service;

import com.ppc.identity.entity.UserInfo;

import java.util.List;

public interface AuthService {
    public List<UserInfo> getAll();
}
