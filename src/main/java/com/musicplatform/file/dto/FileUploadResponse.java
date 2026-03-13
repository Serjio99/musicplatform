package com.musicplatform.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileUploadResponse {

    private FileDto file;
    private String message;
}