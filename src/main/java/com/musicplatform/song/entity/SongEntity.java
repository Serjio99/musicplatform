package com.musicplatform.song.entity;

import com.musicplatform.common.entity.SoftDeletableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "songs")
public class SongEntity extends SoftDeletableEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_file_id")
    private UUID imageFileId;

    @Column(name = "audio_file_id")
    private UUID audioFileId;

    @Column(name = "license_price", precision = 12, scale = 2)
    private BigDecimal licensePrice;

    @Column(name = "economy_price", precision = 12, scale = 2)
    private BigDecimal economyPrice;

    @Column(name = "standard_price", precision = 12, scale = 2)
    private BigDecimal standardPrice;

    @Column(name = "business_price", precision = 12, scale = 2)
    private BigDecimal businessPrice;

    @Column(name = "premium_price", precision = 12, scale = 2)
    private BigDecimal premiumPrice;
}