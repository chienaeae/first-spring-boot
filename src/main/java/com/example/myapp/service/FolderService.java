package com.example.myapp.service;

import com.example.myapp.dto.request.FolderCreate;
import com.example.myapp.entity.FolderEntity;
import com.example.myapp.entity.TodoEntity;
import com.example.myapp.exception.InvalidInputException;
import com.example.myapp.model.Folder;
import com.example.myapp.service.mapper.FolderMapper;
import org.springframework.stereotype.Service;
import com.example.myapp.repository.FolderRepository;

import java.util.Optional;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;

    public FolderService(FolderRepository folderRepository, FolderMapper folderMapper) {
        this.folderRepository = folderRepository;
        this.folderMapper = folderMapper;
    }

    public void putParentFolder(TodoEntity todo, Long parentId) {
        FolderEntity parentFolder = folderRepository.findById(parentId)
                .orElseThrow(() -> new InvalidInputException("Specified parent folder does not exist"));
        todo.setParentFolder(parentFolder);
    }

    public void putParentFolder(FolderEntity folder, Long parentId) {
        FolderEntity parentFolder = folderRepository.findById(parentId)
                .orElseThrow(() -> new InvalidInputException("Specified parent folder does not exist"));
        folder.setParentFolder(parentFolder);
    }

    public Optional<Folder> createFolder(FolderCreate body) {
        FolderEntity folder = new FolderEntity(body.name());
        if(body.parentId() != null) {
            putParentFolder(folder, body.parentId());
        }
        folderRepository.save(folder);
        return Optional.of(folderMapper.toFolder(folder));
    }
}
