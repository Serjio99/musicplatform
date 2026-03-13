
package com.musicplatform.song.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.musicplatform.song.entity.SongEntity;

public interface SongRepository extends JpaRepository<SongEntity, UUID> {
}
