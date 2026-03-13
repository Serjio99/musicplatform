package com.musicplatform.file.controller;

import com.musicplatform.common.dto.ApiResponse;
import com.musicplatform.file.dto.FileDto;
import com.musicplatform.file.entity.FileEntity;
import com.musicplatform.file.service.FileService;
import com.musicplatform.file.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileStorageService fileStorageService;

    @PreAuthorize("hasRole('ADMIN')")
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
        Resource resource = fileStorageService.loadAsResource(entity.getStoragePath());

        return ResponseEntity.ok()
                .contentType(resolveMediaType(entity.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        contentDisposition(entity.getOriginalName()))
                .body(resource);
    }

    @GetMapping("/api/files/stream/{id}")
    public ResponseEntity<?> stream(@PathVariable UUID id,
                                    @RequestHeader(value = "Range", required = false) String rangeHeader) {
        FileEntity entity = fileService.getEntity(id);

        long fileSize = fileStorageService.getFileSize(entity.getStoragePath());
        MediaType mediaType = resolveMediaType(entity.getContentType());

        if (!StringUtils.hasText(rangeHeader)) {
            Resource resource = fileStorageService.loadAsResource(entity.getStoragePath());

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(fileSize)
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .body(resource);
        }

        String rangeValue = rangeHeader.replace("bytes=", "").trim();
        String[] ranges = rangeValue.split("-");

        long start = Long.parseLong(ranges[0]);
        long end = (ranges.length > 1 && StringUtils.hasText(ranges[1]))
                ? Long.parseLong(ranges[1])
                : Math.min(start + 1024 * 1024 - 1, fileSize - 1);

        if (end >= fileSize) {
            end = fileSize - 1;
        }

        byte[] data = fileStorageService.readRange(entity.getStoragePath(), start, end);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(mediaType)
                .contentLength(data.length)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                .body(data);
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

    private String contentDisposition(String originalName) {
        return "inline; filename=\"" + originalName.replace("\"", "") + "\"";
    }
}