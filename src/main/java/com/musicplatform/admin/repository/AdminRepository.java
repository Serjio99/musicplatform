
package com.musicplatform.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.musicplatform.admin.entity.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {
}
