package com.musicplatform.comment.dto;

import com.musicplatform.comment.entity.CommentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentStatusRequest {

    @NotNull
    private CommentStatus status;
}