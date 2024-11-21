package com.project.repository;

import com.project.entity.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class jdbcAdminConfigRepository implements AdminConfigRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addConfig(AdminConfig adminConfig) {
        String query = "INSERT INTO ADMINCONFIG(groupName,division,createOn)VALUES(?,?,?)";
        Object[] args = {adminConfig.getGroupName(),adminConfig.getDivision(),adminConfig.getCreateOn()};
        jdbcTemplate.update(query,args);
    }
}
