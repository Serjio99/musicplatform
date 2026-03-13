package com.musicplatform.request.repository;

import com.musicplatform.request.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<RequestEntity, UUID> {

    List<RequestEntity> findAllByDeletedFalseOrderByCreatedAtDesc();

    List<RequestEntity> findAllByUserIdAndDeletedFalseOrderByCreatedAtDesc(UUID userId);

}