package com.example.myapp.dto.response;

import com.example.myapp.model.Todo;

import java.util.List;

public record FolderWithChildren(
        Long id, String name, List<FolderSimple> subFolders, List<Todo> subTodos
){
}
