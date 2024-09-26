package com.example.myapp.service;

import com.example.myapp.dto.request.FolderCreate;
import com.example.myapp.dto.response.FolderSimple;
import com.example.myapp.dto.response.FolderWithChildren;
import com.example.myapp.entity.FolderEntity;
import com.example.myapp.entity.TodoEntity;
import com.example.myapp.entity.UserEntity;
import com.example.myapp.exception.CustomNotFoundException;
import com.example.myapp.exception.InvalidInputException;
import com.example.myapp.service.mapper.FolderAndTodoMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.myapp.repository.FolderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderAndTodoMapper folderMapper;

    private final UserService userService;

    public FolderService(FolderRepository folderRepository, FolderAndTodoMapper folderMapper, UserService userService) {
        this.folderRepository = folderRepository;
        this.folderMapper = folderMapper;
        this.userService = userService;
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

    public FolderSimple createFolder(Long userId, FolderCreate body) {
        FolderEntity folder = new FolderEntity(body.name());
        folder.setUser(new UserEntity(userId));
        if(body.parentId() != null) {
            putParentFolder(folder, body.parentId());
        }
        folderRepository.save(folder);
        return folderMapper.toFolderSimpleResponse(folder);
    }

    public List<FolderSimple> getRootFolder(Long userId) {
        return folderRepository.findRootFoldersByUserId(userId)
                .stream()
                .map(folderMapper::toFolder)
                .map(folderMapper::toFolderSimpleResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<FolderWithChildren> getFolderWithChildren(Long userId, Long id) {
        return folderRepository.findByIdAndUserId(id, userId)
                .map((folderEntity) -> {
                    // lazy loading children
                    folderEntity.setSubFolders(folderEntity.getSubFolders());
                    folderEntity.setSubTodos(folderEntity.getSubTodos());
                    return folderMapper.toFolderDetailResponse(folderEntity);
                });
    }
}
