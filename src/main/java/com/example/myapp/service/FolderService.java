package com.example.myapp.service;

import com.example.myapp.dto.request.FolderCreate;
import com.example.myapp.dto.response.FolderSimple;
import com.example.myapp.dto.response.FolderWithChildren;
import com.example.myapp.entity.FolderEntity;
import com.example.myapp.entity.TodoEntity;
import com.example.myapp.exception.CustomNotFoundException;
import com.example.myapp.exception.InvalidInputException;
import com.example.myapp.service.mapper.FolderMapper;
import com.example.myapp.service.mapper.TodoMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.myapp.repository.FolderRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public FolderSimple createFolder(FolderCreate body) {
        FolderEntity folder = new FolderEntity(body.name());
        if(body.parentId() != null) {
            putParentFolder(folder, body.parentId());
        }
        folderRepository.save(folder);
        return folderMapper.toSimpleResponse(folder);
    }

    public List<FolderSimple> getRootFolder() {
        return folderRepository.findRootFolders()
                .stream()
                .map(folderMapper::toFolder)
                .map(folderMapper::toSimpleResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public FolderWithChildren getFolderWithChildren(Long id) {
        return folderRepository.findById(id)
                .map((folderEntity) -> {
                    // lazy loading children
                    folderEntity.setSubFolders(folderEntity.getSubFolders());
                    folderEntity.setSubTodos(folderEntity.getSubTodos());
                    return folderMapper.toDetailResponse(folderEntity);
                }).orElseThrow(() -> new CustomNotFoundException("Folder not found"));
    }
}
