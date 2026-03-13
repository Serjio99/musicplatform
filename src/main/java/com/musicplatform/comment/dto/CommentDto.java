package com.musicplatform.comment.dto;

import com.musicplatform.comment.entity.CommentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class CommentDto {

    private UUID id;
    private UUID userId;
    private String userName;
    private UUID songId;
    private String songName;
    private String text;
    private CommentStatus status;
    private OffsetDateTime createdAt;
}