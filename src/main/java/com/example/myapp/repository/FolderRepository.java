package com.example.myapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.myapp.entity.FolderEntity;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends CrudRepository<FolderEntity, Long> {
    List<FolderEntity> findByName(String name);
    Optional<FolderEntity> findById(long id);

    List<FolderEntity> findByParentFolderId(Long parentFolderId);

    @Query("SELECT f FROM FolderEntity f WHERE f.parentFolder IS NULL")
    List<FolderEntity> findRootFolders();
}
