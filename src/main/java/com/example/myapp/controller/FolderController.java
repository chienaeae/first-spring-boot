package com.example.myapp.controller;

import com.example.myapp.dto.request.FolderCreate;
import com.example.myapp.dto.response.FolderSimple;
import com.example.myapp.dto.response.FolderWithChildren;
import com.example.myapp.service.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("")
    public ResponseEntity<FolderSimple> addFolder(@RequestBody @Validated FolderCreate body) {
        return ResponseEntity.ok(folderService.createFolder(body));
    }

    @GetMapping("/root")
    public ResponseEntity<List<FolderSimple>> getRootFolders() {
        return ResponseEntity.ok(folderService.getRootFolder());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolderWithChildren> getFolder(@PathVariable Long id) {
        return ResponseEntity.ok(folderService.getFolderWithChildren(id));
    }
}
