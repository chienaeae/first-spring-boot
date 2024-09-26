package com.example.myapp.service;

import com.example.myapp.dto.request.FolderCreate;
import com.example.myapp.dto.response.FolderSimple;
import com.example.myapp.dto.response.FolderWithChildren;
import com.example.myapp.entity.FolderEntity;
import com.example.myapp.entity.TodoEntity;
import com.example.myapp.entity.UserEntity;
import com.example.myapp.exception.BadException;
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
    private final FolderAndTodoMapper folderAndTodoMapper;


    public FolderService(FolderRepository folderRepository, FolderAndTodoMapper folderAndTodoMapper, UserService userService) {
        this.folderRepository = folderRepository;
        this.folderAndTodoMapper = folderAndTodoMapper;
    }

    public void putParentFolder(TodoEntity todo, Long parentId) {
        FolderEntity parentFolder = folderRepository.findById(parentId)
                .orElseThrow(() -> new BadException("Specified parent folder does not exist"));
        todo.setParentFolder(parentFolder);
    }

    public void putParentFolder(FolderEntity folder, Long parentId) {
        FolderEntity parentFolder = folderRepository.findById(parentId)
                .orElseThrow(() -> new BadException("Specified parent folder does not exist"));
        folder.setParentFolder(parentFolder);
    }

    public Optional<FolderSimple> createFolder(Long userId, FolderCreate body) {
        FolderEntity folder = new FolderEntity(body.name());
        folder.setUser(new UserEntity(userId));
        if(body.parentId() != null) {
            putParentFolder(folder, body.parentId());
        }

        return Optional
                .of(folderRepository.save(folder))
                .map(folderAndTodoMapper::toFolderSimpleResponse);
    }

    public List<FolderSimple> getRootFolder(Long userId) {
        return folderRepository.findRootFoldersByUserId(userId)
                .stream()
                .map(folderAndTodoMapper::toFolder)
                .map(folderAndTodoMapper::toFolderSimpleResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<FolderWithChildren> getFolderWithChildren(Long userId, Long id) {
        return folderRepository.findByIdAndUserId(id, userId)
                .map((folderEntity) -> {
                    // lazy loading children
                    folderEntity.setSubFolders(folderEntity.getSubFolders());
                    folderEntity.setSubTodos(folderEntity.getSubTodos());
                    return folderAndTodoMapper.toFolderDetailResponse(folderEntity);
                });
    }
}
