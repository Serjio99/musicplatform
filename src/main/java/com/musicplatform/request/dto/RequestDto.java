package com.musicplatform.request.dto;

import com.musicplatform.request.entity.LicenseType;
import com.musicplatform.request.entity.RequestStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class RequestDto {

    private UUID id;

    private UUID userId;
    private String userName;

    private UUID songId;
    private String songName;

    private LicenseType licenseType;

    private BigDecimal price;

    private RequestStatus status;

    private String comment;

    private OffsetDateTime createdAt;
}