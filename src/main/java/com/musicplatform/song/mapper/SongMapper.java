package com.musicplatform.song.mapper;

import com.musicplatform.song.dto.SongDto;
import com.musicplatform.song.entity.SongEntity;

public class SongMapper {

    public static SongDto toDto(SongEntity entity) {
        return SongDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imageFileId(entity.getImageFileId())
                .audioFileId(entity.getAudioFileId())
                .licensePrice(entity.getLicensePrice())
                .economyPrice(entity.getEconomyPrice())
                .standardPrice(entity.getStandardPrice())
                .businessPrice(entity.getBusinessPrice())
                .premiumPrice(entity.getPremiumPrice())
                .build();
    }
}