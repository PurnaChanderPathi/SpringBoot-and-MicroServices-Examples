package com.project.repository;

import com.project.Dto.FileData;
import com.project.entity.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class JdbcFileRepository implements FileRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public FileData getFileByFileId(Long fileId) {
        String query = "SELECT reviewId,fileName,fileType,data,size,comment FROM DOCUMENT WHERE fileId=?";
        Object[] args = {fileId};
        return jdbcTemplate.queryForObject(query, args, new FileDataRowMapper());
    }

    @Override
    public List<UploadedFile> getFileByReviewId(String reviewId) {
        String query = "SELECT * FROM DOCUMENT WHERE reviewId = ?";
        Object[] args = {reviewId};
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(UploadedFile.class),args);
    }

    private static class FileDataRowMapper implements RowMapper<FileData> {
        @Override
        public FileData mapRow(ResultSet rs, int rowNum) throws SQLException {
            FileData fileData = new FileData();
            fileData.setFileName(rs.getString("fileName"));
            fileData.setFileData(rs.getBytes("data"));
            fileData.setContentType(rs.getString("fileType"));
            return fileData;
        }
    }

}

