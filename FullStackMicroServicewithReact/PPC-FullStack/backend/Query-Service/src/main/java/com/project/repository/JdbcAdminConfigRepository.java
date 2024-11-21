package com.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class JdbcAdminConfigRepository implements AdminConfigRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getGroupNames() {
        String query = "SELECT DISTINCT groupName FROM adminconfig";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("groupName"));
    }

    @Override
    public List<String> getDivisions(String groupName) {
        String query = "SELECT division FROM adminconfig WHERE groupName = ?";
        Object[] args = {groupName};
        return jdbcTemplate.query(query,args,(rs, rowNum) -> rs.getString("division") );
    }
}
