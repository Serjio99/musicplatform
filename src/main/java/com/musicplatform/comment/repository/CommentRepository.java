
package com.musicplatform.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.musicplatform.comment.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
}
