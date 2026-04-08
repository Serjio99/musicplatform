package com.musicplatform.comment.controller;

import com.musicplatform.comment.dto.CommentDto;
import com.musicplatform.comment.dto.CreateCommentRequest;
import com.musicplatform.comment.dto.UpdateCommentStatusRequest;
import com.musicplatform.comment.service.CommentService;
import com.musicplatform.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/comments")
    public ApiResponse<List<CommentDto>> getPublicComments() {
        return ApiResponse.success(commentService.getPublicComments());
    }

    @GetMapping("/api/comments/song/{songId}")
    public ApiResponse<List<CommentDto>> getPublicCommentsBySong(@PathVariable UUID songId) {
        return ApiResponse.success(commentService.getPublicCommentsBySong(songId));
    }

    @PostMapping("/api/comments")
    public ApiResponse<CommentDto> create(@Valid @RequestBody CreateCommentRequest request) {
        return ApiResponse.success(commentService.createComment(request), "Комментарий создан");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/comments")
    public ApiResponse<List<CommentDto>> getAllAdminComments() {
        return ApiResponse.success(commentService.getAllAdminComments());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/api/admin/comments/{id}/status")
    public ApiResponse<CommentDto> updateStatus(@PathVariable UUID id,
                                                @Valid @RequestBody UpdateCommentStatusRequest request) {
        return ApiResponse.success(commentService.updateStatus(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/admin/comments/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        commentService.delete(id);
        return ApiResponse.success(null, "Комментарий удалён");
    }
}
