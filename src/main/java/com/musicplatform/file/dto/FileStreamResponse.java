package com.musicplatform.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileStreamResponse {

    private String fileName;
    private String contentType;
    private long contentLength;
    private boolean partialContent;
}