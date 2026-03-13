package com.musicplatform.file.service;

import com.musicplatform.common.exception.NotFoundException;
import com.musicplatform.file.dto.FileDto;
import com.musicplatform.file.entity.FileEntity;
import com.musicplatform.file.mapper.FileMapper;
import com.musicplatform.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    public FileDto upload(MultipartFile multipartFile) {
        FileStorageService.StoredFileResult stored = fileStorageService.store(multipartFile);

        FileEntity entity = new FileEntity();
        entity.setId(UUID.randomUUID());
        entity.setOriginalName(stored.originalName());
        entity.setStorageName(stored.storageName());
        entity.setContentType(stored.contentType());
        entity.setSizeBytes(stored.sizeBytes());
        entity.setStoragePath(stored.storagePath());
        entity.setPublicUrl("/api/files/" + entity.getId());
        entity.setImage(isImage(stored.contentType()));
        entity.setAudio(isAudio(stored.contentType()));

        fileRepository.save(entity);

        entity.setPublicUrl("/api/files/" + entity.getId());
        fileRepository.save(entity);

        return FileMapper.toDto(entity);
    }

    public FileEntity getEntity(UUID id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
    }

    public FileDto getById(UUID id) {
        return FileMapper.toDto(getEntity(id));
    }

    private boolean isImage(String contentType) {
        return contentType != null && contentType.startsWith("image/");
    }

    private boolean isAudio(String contentType) {
        return contentType != null && contentType.startsWith("audio/");
    }
}