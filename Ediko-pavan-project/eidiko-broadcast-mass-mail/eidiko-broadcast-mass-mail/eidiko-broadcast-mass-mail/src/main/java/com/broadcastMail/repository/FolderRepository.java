package com.broadcastMail.repository;

import com.broadcastMail.entites.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<FolderEntity,Long> {
    FolderEntity findByFolderName(String folderName);

  //  List<FolderEntity> findByAllFolderName(String folderName);


}
