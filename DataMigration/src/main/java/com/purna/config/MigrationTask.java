package com.purna.config;

import com.purna.model.MigrationConfig;
import com.purna.model.SourceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MigrationTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(MigrationTask.class);
    private final JdbcTemplate jdbcTemplate;
    private final MigrationConfig config;
    private final DataSourceTransactionManager transactionManager;
    private int rollbackCount = 0;

    public MigrationTask(JdbcTemplate jdbcTemplate, MigrationConfig config, DataSourceTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;
        this.transactionManager = transactionManager;
    }

    @Override
    public void run() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    String selectSql = String.format("SELECT * FROM %s WHERE status = 'Completed'", config.getSourceTable());
                    List<Map<String, Object>> completedRows = jdbcTemplate.query(selectSql, new RowMapper<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Map<String, Object> row = new HashMap<>();
                            row.put("id", rs.getLong("id"));
                            row.put("status", rs.getString("status"));
                            int columnCount = rs.getMetaData().getColumnCount();
                            for (int i = 1; i <= columnCount; i++) {
                                String columnName = rs.getMetaData().getColumnName(i);
                                if (!columnName.equals("id") && !columnName.equals("status")) {
                                    row.put(columnName, rs.getObject(i));
                                }
                            }
                            return row;
                        }
                    });

                    for (Map<String, Object> row : completedRows) {
                        StringBuilder insertSql = new StringBuilder(String.format("INSERT INTO %s (", config.getTargetTable()));
                        StringBuilder valuesPlaceholder = new StringBuilder();

                        for (String column : row.keySet()) {
                            insertSql.append(column).append(", ");
                            valuesPlaceholder.append("?, ");
                        }

                        insertSql.setLength(insertSql.length() - 2);
                        valuesPlaceholder.setLength(valuesPlaceholder.length() - 2);

                        insertSql.append(") VALUES (").append(valuesPlaceholder).append(")");

                        try {
                            jdbcTemplate.update(insertSql.toString(), row.values().toArray());
                            System.out.println("Inserted row with ID " + row.get("id"));
                            // Sleep for 10 seconds
                            Thread.sleep(30000);
                        } catch (Exception e) {
                            status.setRollbackOnly();
                            rollbackCount++;
                            System.err.println("Error inserting row with ID " + row.get("id") + ": " + e.getMessage());
                            throw new RuntimeException("Stopping migration due to error on row ID " + row.get("id"));
                        }
                    }

                    String deleteSql = String.format("DELETE FROM %s WHERE status = 'Completed'", config.getSourceTable());
                    jdbcTemplate.update(deleteSql);
//                    System.out.println("Deleted processed rows from source table.");

                } catch (Exception e) {
                    status.setRollbackOnly();
                    System.err.println("Error during migration: " + e.getMessage());
                }
                return null;
            }
        });
    }
}