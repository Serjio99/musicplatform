package com.musicplatform.request.mapper;

import com.musicplatform.request.dto.RequestDto;
import com.musicplatform.request.entity.RequestEntity;

public class RequestMapper {

    private RequestMapper() {
    }

    public static RequestDto toDto(RequestEntity entity) {

        if (entity == null) {
            return null;
        }

        return RequestDto.builder()
                .id(entity.getId())

                .userId(entity.getUser().getId())
                .userName(entity.getUser().getName())

                .songId(entity.getSong().getId())
                .songName(entity.getSong().getName())

                .status(entity.getStatus())

                .comment(entity.getComment())

                .price(entity.getPrice())

                .createdAt(entity.getCreatedAt())

                .build();
    }
}
