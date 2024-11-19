package com.project.repository;

import com.project.Dto.FileData;
import com.project.entity.UploadedFile;

public interface FileRepository {

    String saveFile(UploadedFile uploadedFile);

//    FileData getFileByFileId(Long fileId);

    String deleteByFileId(Long fileId);
}
