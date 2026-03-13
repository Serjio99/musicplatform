package com.musicplatform.song.repository;

import com.musicplatform.song.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SongRepository extends JpaRepository<SongEntity, UUID> {

    List<SongEntity> findAllByDeletedFalseOrderByCreatedAtDesc();
}