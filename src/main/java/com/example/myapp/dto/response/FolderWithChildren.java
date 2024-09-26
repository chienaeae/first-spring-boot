package com.example.myapp.dto.response;

import java.util.List;

public record FolderWithChildren(
        Long id, String name, List<FolderSimple> subFolders, List<TodoSimple> subTodos
){
}
