package com.project.service;

import com.project.repository.AdminConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminConfigService {

    @Autowired
    private AdminConfigRepository adminConfigRepository;

    public List<String> getGroupNames(){
        return adminConfigRepository.getGroupNames();
    }

    public List<String> getDivisions(String groupName){
        return adminConfigRepository.getDivisions(groupName);
    }

    public List<String> getSpocs(String division){
        return adminConfigRepository.getSpocs(division);
    }
}
