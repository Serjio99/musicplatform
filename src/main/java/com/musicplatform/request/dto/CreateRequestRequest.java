package com.musicplatform.request.dto;

import com.musicplatform.request.entity.LicenseType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateRequestRequest {

    @NotNull
    private UUID songId;

    @NotNull
    private LicenseType licenseType;

    private String comment;

}