package com.example.myapp.controller;

import com.example.myapp.dto.CurrentUser;
import com.example.myapp.dto.request.FolderCreate;
import com.example.myapp.dto.response.FolderSimple;
import com.example.myapp.dto.response.FolderWithChildren;
import com.example.myapp.exception.BadException;
import com.example.myapp.exception.CustomNotFoundException;
import com.example.myapp.service.AuthService;
import com.example.myapp.service.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {

    private final AuthService authService;
    private final FolderService folderService;

    public FolderController(AuthService authService, FolderService folderService) {
        this.authService = authService;
        this.folderService = folderService;
    }

    @PostMapping("")
    public ResponseEntity<FolderSimple> addFolder(@RequestBody @Validated FolderCreate body) {
        CurrentUser currentUser = authService.getCurrentUser();

        return folderService.createFolder(currentUser.userId(), body)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomNotFoundException("Failed to create folder"));
    }

    @GetMapping("/root")
    public ResponseEntity<List<FolderSimple>> getRootFolders() {
        CurrentUser currentUser = authService.getCurrentUser();

        return ResponseEntity.ok(folderService.getRootFolder(currentUser.userId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolderWithChildren> getFolder(@PathVariable Long id) {
        CurrentUser currentUser = authService.getCurrentUser();

        FolderWithChildren folder = folderService.getFolderWithChildren(currentUser.userId(), id)
                .orElseThrow(() -> new BadException("Folder not found"));

        return ResponseEntity.ok(folder);
    }
}
