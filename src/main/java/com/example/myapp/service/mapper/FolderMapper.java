package com.example.myapp.service.mapper;

import com.example.myapp.entity.FolderEntity;
import com.example.myapp.model.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FolderMapper {
    Folder toFolder(FolderEntity folderEntity);

    List<Folder> toList(List<FolderEntity> folderEntityList);
}
