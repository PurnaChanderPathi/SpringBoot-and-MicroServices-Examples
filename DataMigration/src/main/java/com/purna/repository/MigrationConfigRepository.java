package com.purna.repository;

import com.purna.model.MigrationConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MigrationConfigRepository {

    private final JdbcTemplate jdbcTemplate;

    public MigrationConfigRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MigrationConfig> findAll() {
        String sql = "SELECT source_table, target_table FROM student.migration_config";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new MigrationConfig(rs.getString("source_table"), rs.getString("target_table"),"Completed")
        );
    }
}
