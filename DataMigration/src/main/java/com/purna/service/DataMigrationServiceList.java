package com.purna.service;

import com.purna.config.MigrationTask;
import com.purna.model.MigrationConfig;
import com.purna.repository.MigrationConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DataMigrationServiceList {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MigrationConfigRepository migrationConfigRepository;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Scheduled(fixedRate = 100000) // Check every minute
    public void migrateData() {
        List<MigrationConfig> migrationConfigs = migrationConfigRepository.findAll();

        ExecutorService executorService = Executors.newFixedThreadPool(migrationConfigs.size());

        for (MigrationConfig config : migrationConfigs) {
            executorService.submit(new MigrationTask(jdbcTemplate, config, transactionManager)); // Pass Transaction Manager
        }

        executorService.shutdown();
    }
}