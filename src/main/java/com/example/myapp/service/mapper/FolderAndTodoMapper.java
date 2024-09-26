package com.example.myapp.service.mapper;

import com.example.myapp.dto.response.FolderSimple;
import com.example.myapp.dto.response.FolderWithChildren;
import com.example.myapp.dto.response.TodoSimple;
import com.example.myapp.entity.FolderEntity;
import com.example.myapp.entity.TodoEntity;
import com.example.myapp.model.Folder;
import com.example.myapp.model.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FolderAndTodoMapper {
    @Mappings({
        @Mapping(source = "user.id", target = "userId"),
        @Mapping(source = "subFolders", target = "subFolders"),
        @Mapping(source = "subTodos", target = "subTodos")
    })
    Folder toFolder(FolderEntity folderEntity);

    @Mapping(source = "user.id", target = "userId")
    List<Folder> toFolderList(List<FolderEntity> folderEntityList);

    FolderSimple toFolderSimpleResponse(FolderEntity folderEntity);

    FolderSimple toFolderSimpleResponse(Folder folder);

    FolderWithChildren toFolderDetailResponse(FolderEntity folderEntity);

    @Mapping(source = "user.id", target = "userId")
    Todo toTodo(TodoEntity todoEntity);

    @Mapping(source = "user.id", target = "userId")
    List<Todo> toTodoList(List<TodoEntity> todoEntityList);

    TodoSimple toTodoSimpleResponse(TodoEntity todoEntity);
}
