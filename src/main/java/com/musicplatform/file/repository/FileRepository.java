
package com.musicplatform.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.musicplatform.file.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {
}
