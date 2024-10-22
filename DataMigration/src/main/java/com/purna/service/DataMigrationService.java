//package com.purna.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//@Slf4j
//public class DataMigrationService {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Scheduled(fixedRate = 60000)
//    @Transactional
//    public void migrateCompletedData() {
//        String selectQuery = "SELECT * FROM student.sourcedata WHERE status = 'completed'";
//        List<Map<String, Object>> completedData = jdbcTemplate.queryForList(selectQuery);
//
//        for (Map<String, Object> row : completedData) {
//            try {
//                String insertQuery = "INSERT INTO datasource.duplicate (id, status, data) VALUES (?, ?, ?)";
//                jdbcTemplate.update(insertQuery, row.get("id"), row.get("status"), row.get("data"));
//
//                String deleteQuery = "DELETE FROM student.sourcedata WHERE id = ?";
//                jdbcTemplate.update(deleteQuery, row.get("id"));
//
//            } catch (Exception e) {
//                System.err.println("Error migrating data: " + e.getMessage());
//                throw e;
//            }
//        }
//    }
//}
