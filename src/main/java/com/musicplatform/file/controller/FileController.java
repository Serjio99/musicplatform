package com.musicplatform.file.controller;

import com.musicplatform.common.dto.ApiResponse;
import com.musicplatform.file.dto.FileDto;
import com.musicplatform.file.service.FileService;
import com.musicplatform.file.entity.FileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/api/admin/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileDto> upload(@RequestPart("file") MultipartFile file) {
        return ApiResponse.success(fileService.upload(file), "Файл успешно загружен");
    }

    @GetMapping("/api/files/{id}/meta")
    public ApiResponse<FileDto> getMeta(@PathVariable UUID id) {
        return ApiResponse.success(fileService.getById(id));
    }

    @GetMapping("/api/files/{id}")
    public ResponseEntity<Resource> download(@PathVariable UUID id) {
        FileEntity entity = fileService.getEntity(id);

        Resource resource = new FileSystemResource(Path.of(entity.getStoragePath()));

        return ResponseEntity.ok()
                .contentType(resolveMediaType(entity.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + entity.getOriginalName() + "\"")
                .body(resource);
    }

    private MediaType resolveMediaType(String contentType) {
        try {
            return contentType == null
                    ? MediaType.APPLICATION_OCTET_STREAM
                    : MediaType.parseMediaType(contentType);
        } catch (Exception ex) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}