package com.musicplatform.file.service;

import com.musicplatform.common.exception.BadRequestException;
import com.musicplatform.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${app.file-storage.upload-dir:uploads}")
    private String uploadDir;

    public StoredFileResult store(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new BadRequestException("Файл не был передан");
        }

        String originalName = StringUtils.cleanPath(
                multipartFile.getOriginalFilename() == null ? "file" : multipartFile.getOriginalFilename()
        );

        String extension = extractExtension(originalName);
        String storageName = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            Path targetPath = uploadPath.resolve(storageName);
            Files.copy(multipartFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return new StoredFileResult(
                    originalName,
                    storageName,
                    multipartFile.getContentType(),
                    multipartFile.getSize(),
                    targetPath.toString()
            );
        } catch (IOException ex) {
            throw new BadRequestException("Не удалось сохранить файл");
        }
    }

    public Resource loadAsResource(String storagePath) {
        Path path = Paths.get(storagePath).toAbsolutePath().normalize();

        if (!Files.exists(path)) {
            throw new NotFoundException("Файл не найден на диске");
        }

        return new FileSystemResource(path);
    }

    public byte[] readRange(String storagePath, long start, long end) {
        Path path = Paths.get(storagePath).toAbsolutePath().normalize();

        if (!Files.exists(path)) {
            throw new NotFoundException("Файл не найден на диске");
        }

        long contentLength = end - start + 1;

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(path.toFile(), "r")) {
            byte[] data = new byte[(int) contentLength];
            randomAccessFile.seek(start);
            randomAccessFile.readFully(data);
            return data;
        } catch (IOException ex) {
            throw new BadRequestException("Не удалось прочитать диапазон файла");
        }
    }

    public long getFileSize(String storagePath) {
        try {
            return Files.size(Paths.get(storagePath).toAbsolutePath().normalize());
        } catch (IOException ex) {
            throw new BadRequestException("Не удалось определить размер файла");
        }
    }

    private String extractExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex < 0 || lastDotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }

    public record StoredFileResult(
            String originalName,
            String storageName,
            String contentType,
            long sizeBytes,
            String storagePath
    ) {
    }
}