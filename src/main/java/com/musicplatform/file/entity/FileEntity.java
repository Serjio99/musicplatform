package com.musicplatform.file.entity;

import com.musicplatform.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "files")
public class FileEntity extends BaseEntity {

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "storage_name", nullable = false, unique = true)
    private String storageName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "public_url")
    private String publicUrl;

    @Column(name = "is_image", nullable = false)
    private boolean image;

    @Column(name = "is_audio", nullable = false)
    private boolean audio;
}