package com.example.myapp.controller;

import com.example.myapp.dto.request.FolderCreate;
import com.example.myapp.exception.InternalException;
import com.example.myapp.model.Folder;
import com.example.myapp.service.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/folder")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("")
    public @ResponseBody ResponseEntity<Folder> addFolder(@RequestBody @Validated FolderCreate body) {
        Optional<Folder> newFolder =  folderService.createFolder(body);
        if(newFolder.isPresent()) {
            Folder folder = newFolder.get();
            folder.setSubFolders(null);
            folder.setSubTodos(null);
            return ResponseEntity.ok(folder);
        } else {
            throw new InternalException("Failed to create folder");
        }
    }
}
