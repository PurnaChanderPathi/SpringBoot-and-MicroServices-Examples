package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.FolderEntity;



@Repository
@Transactional
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {

	FolderEntity findByFolderName(String extractedFileName);
     

    void deleteByFolderName(String folderName);

}
