package com.project.repository;

import com.project.Dto.FileData;
import com.project.entity.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class JdbcFileRepository implements FileRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String saveFile(UploadedFile uploadedFile) {

        String query = "INSERT INTO DOCUMENT (reviewId,fileName,fileType,data,size,comment)VALUES(?,?,?,?,?,?)";
        Object[] args = {
                uploadedFile.getReviewId(),
                uploadedFile.getFileName(),
                uploadedFile.getFileType(),
                uploadedFile.getData(),
                uploadedFile.getSize(),
                uploadedFile.getComment()
        };
        jdbcTemplate.update(query, args);
        return "File Uploaded SuccessFully";
    }

    @Override
    public FileData getFileByFileId(Long fileId) {
        String query = "SELECT reviewId,fileName,fileType,data,size,comment FROM DOCUMENT WHERE fileId=?";
        Object[] args = {fileId};
        return jdbcTemplate.queryForObject(query, args, new FileDataRowMapper());
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

