
package com.musicplatform.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.musicplatform.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
