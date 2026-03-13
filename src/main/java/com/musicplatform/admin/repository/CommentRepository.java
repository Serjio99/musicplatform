package com.musicplatform.comment.repository;

import com.musicplatform.comment.entity.CommentEntity;
import com.musicplatform.comment.entity.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {

    List<CommentEntity> findAllByDeletedFalseAndStatusOrderByCreatedAtDesc(CommentStatus status);

    List<CommentEntity> findAllByDeletedFalseOrderByCreatedAtDesc();

    List<CommentEntity> findAllBySongIdAndDeletedFalseAndStatusOrderByCreatedAtDesc(UUID songId, CommentStatus status);

    Page<CommentEntity> findByStatusOrderByCreatedAtDesc(CommentStatus status, Pageable pageable);

    Page<CommentEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    long countByStatus(CommentStatus status);
}