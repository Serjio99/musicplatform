package com.musicplatform.auth.repository;

import com.musicplatform.auth.entity.RefreshTokenEntity;
import com.musicplatform.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByTokenAndRevokedFalse(String token);

    void deleteAllByUser(UserEntity user);
}