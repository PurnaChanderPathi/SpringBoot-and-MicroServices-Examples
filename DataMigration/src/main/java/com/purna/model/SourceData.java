package com.purna.model;

public class SourceData {

    private Long id;
    private String status;
    private String data;

    public SourceData() {
    }

    public SourceData(Long id, String status, String data) {
        this.id = id;
        this.status = status;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
