package com.example.myapp.service.mapper;

import com.example.myapp.dto.response.FolderSimple;
import com.example.myapp.dto.response.FolderWithChildren;
import com.example.myapp.entity.FolderEntity;
import com.example.myapp.model.Folder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FolderMapper {
    Folder toFolder(FolderEntity folderEntity);

    List<Folder> toList(List<FolderEntity> folderEntityList);

    FolderSimple toSimpleResponse(FolderEntity folderEntity);

    FolderSimple toSimpleResponse(Folder folder);

    FolderWithChildren toDetailResponse(FolderEntity folderEntity);
}
