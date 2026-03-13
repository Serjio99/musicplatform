package com.musicplatform.song.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateSongRequest {

    @NotBlank
    private String name;

    private String description;

    private UUID imageFileId;

    private UUID audioFileId;

    private BigDecimal licensePrice;

    private BigDecimal economyPrice;

    private BigDecimal standardPrice;

    private BigDecimal businessPrice;

    private BigDecimal premiumPrice;
}