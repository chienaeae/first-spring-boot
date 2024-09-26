package com.example.myapp.controller;

import com.example.myapp.dto.CurrentUser;
import com.example.myapp.dto.request.FolderCreate;
import com.example.myapp.dto.response.FolderSimple;
import com.example.myapp.dto.response.FolderWithChildren;
import com.example.myapp.exception.CustomNotFoundException;
import com.example.myapp.model.User;
import com.example.myapp.service.AuthService;
import com.example.myapp.service.FolderService;
import com.example.myapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {

    private final AuthService authService;
    private final UserService userService;
    private final FolderService folderService;

    public FolderController(AuthService authService, UserService userService, FolderService folderService) {
        this.authService = authService;
        this.userService = userService;
        this.folderService = folderService;
    }

    @PostMapping("")
    public ResponseEntity<FolderSimple> addFolder(@RequestBody @Validated FolderCreate body) {
        CurrentUser currentUser = authService.getCurrentUser();

        return ResponseEntity.ok(folderService.createFolder(currentUser.userId(), body));
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
                .orElseThrow(() -> new CustomNotFoundException("Folder not found"));

        return ResponseEntity.ok(folder);
    }
}
