package com.musicplatform.comment.mapper;

import com.musicplatform.comment.dto.CommentDto;
import com.musicplatform.comment.entity.CommentEntity;

public class CommentMapper {

    public static CommentDto toDto(CommentEntity entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .userName(entity.getUser().getName())
                .songId(entity.getSong().getId())
                .songName(entity.getSong().getName())
                .text(entity.getText())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}