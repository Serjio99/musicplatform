package com.musicplatform.file.mapper;

import com.musicplatform.file.dto.FileDto;
import com.musicplatform.file.entity.FileEntity;

public class FileMapper {

    public static FileDto toDto(FileEntity entity) {
        return FileDto.builder()
                .id(entity.getId())
                .originalName(entity.getOriginalName())
                .storageName(entity.getStorageName())
                .contentType(entity.getContentType())
                .sizeBytes(entity.getSizeBytes())
                .storagePath(entity.getStoragePath())
                .publicUrl(entity.getPublicUrl())
                .image(entity.isImage())
                .audio(entity.isAudio())
                .build();
    }
}