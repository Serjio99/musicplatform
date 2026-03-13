
package com.musicplatform.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.musicplatform.auth.entity.AuthEntity;

public interface AuthRepository extends JpaRepository<AuthEntity, UUID> {
}
