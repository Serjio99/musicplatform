package com.musicplatform.comment.controller;

import com.musicplatform.comment.dto.CommentDto;
import com.musicplatform.comment.dto.CreateCommentRequest;
import com.musicplatform.comment.dto.UpdateCommentStatusRequest;
import com.musicplatform.comment.service.CommentService;
import com.musicplatform.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comments")
    public ApiResponse<CommentDto> createComment(@Valid @RequestBody CreateCommentRequest request) {
        return ApiResponse.success(commentService.createComment(request), "Комментарий отправлен на модерацию");
    }

    @GetMapping("/api/comments/public")
    public ApiResponse<List<CommentDto>> getPublicComments() {
        return ApiResponse.success(commentService.getPublicComments());
    }

    @GetMapping("/api/comments/public/song/{songId}")
    public ApiResponse<List<CommentDto>> getPublicCommentsBySong(@PathVariable UUID songId) {
        return ApiResponse.success(commentService.getPublicCommentsBySong(songId));
    }

    @GetMapping("/api/admin/comments")
    public ApiResponse<List<CommentDto>> getAllAdminComments() {
        return ApiResponse.success(commentService.getAllAdminComments());
    }

    @PatchMapping("/api/admin/comments/{id}/status")
    public ApiResponse<CommentDto> updateStatus(@PathVariable UUID id,
                                                @Valid @RequestBody UpdateCommentStatusRequest request) {
        return ApiResponse.success(commentService.updateStatus(id, request), "Статус комментария обновлён");
    }

    @DeleteMapping("/api/admin/comments/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        commentService.delete(id);
        return ApiResponse.success(null, "Комментарий удалён");
    }
}