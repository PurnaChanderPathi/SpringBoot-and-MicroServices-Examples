package com.project.repository;

import com.project.Dto.FileData;
import com.project.entity.UploadedFile;

import java.util.List;

public interface FileRepository {

    FileData getFileByFileId(Long fileId);

    List<UploadedFile> getFileByReviewId(String reviewId);
}
