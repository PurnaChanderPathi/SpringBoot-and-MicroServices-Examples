package com.project.service;

import com.project.entity.AdminConfig;
import com.project.repository.AdminConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminConfigService {

    @Autowired
    private AdminConfigRepository adminConfigRepository;

    public void addConfig(AdminConfig adminConfig){
        Date date = new Date();
        adminConfig.setCreateOn(date);
        adminConfigRepository.addConfig(adminConfig);
    }
}
