package com.purna.model;

public class MigrationConfig {
    private String sourceTable;
    private String targetTable;
    private String status; // Add status field

    public MigrationConfig(String sourceTable, String targetTable, String status) {
        this.sourceTable = sourceTable;
        this.targetTable = targetTable;
        this.status = status;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public String getStatus() {
        return status;
    }
}
