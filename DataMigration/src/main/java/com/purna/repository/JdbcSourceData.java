package com.purna.repository;

import com.purna.model.SourceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcSourceData implements SourceDataRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(SourceData sourceData) {
        String query = "INSERT into SourceData(id,status,data)values(?,?,?)";
        Object [] args = {sourceData.getId(),sourceData.getStatus(),sourceData.getData()};
        return jdbcTemplate.update(query,args);
    }

    @Override
    public int update(SourceData sourceData) {
        String query = "UPDATE SourceData set status=?,data=? where id=?";
        Object [] args = {sourceData.getStatus(),sourceData.getData()};
        return jdbcTemplate.update(query,args);
    }

    @Override
    public SourceData findById(Long id) {
        String query = "SELECT * FROM SourceData WHERE id = ?";
        Object [] args = {id};
        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(SourceData.class),args);
    }

    @Override
    public int deleteById(Long id) {
        String query = "DELETE FROM SourceData WHERE id = ?";
        Object [] args = {id};
        return jdbcTemplate.update(query, args);
    }

    @Override
    public List<SourceData> findAll() {
        String query = "SELECT * FROM persons";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(SourceData.class));
    }
}
