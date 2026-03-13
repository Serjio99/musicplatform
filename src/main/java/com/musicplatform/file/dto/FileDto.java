package com.musicplatform.file.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FileDto {

    private UUID id;
    private String originalName;
    private String storageName;
    private String contentType;
    private Long sizeBytes;
    private String storagePath;
    private String publicUrl;
    private boolean image;
    private boolean audio;
}