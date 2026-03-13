
package com.musicplatform.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.musicplatform.request.entity.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, UUID> {
}
