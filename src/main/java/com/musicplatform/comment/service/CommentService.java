package com.musicplatform.comment.service;

import com.musicplatform.comment.dto.CommentDto;
import com.musicplatform.comment.dto.CreateCommentRequest;
import com.musicplatform.comment.dto.UpdateCommentStatusRequest;
import com.musicplatform.comment.entity.CommentEntity;
import com.musicplatform.comment.entity.CommentStatus;
import com.musicplatform.comment.mapper.CommentMapper;
import com.musicplatform.comment.repository.CommentRepository;
import com.musicplatform.common.exception.NotFoundException;
import com.musicplatform.security.SecurityUtils;
import com.musicplatform.song.entity.SongEntity;
import com.musicplatform.song.repository.SongRepository;
import com.musicplatform.user.entity.UserEntity;
import com.musicplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public CommentDto createComment(CreateCommentRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        UserEntity user = userRepository.findById(currentUserId)
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(() -> new NotFoundException("User not found"));

        SongEntity song = songRepository.findById(request.getSongId())
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(() -> new NotFoundException("Song not found"));

        CommentEntity comment = new CommentEntity();
        comment.setId(UUID.randomUUID());
        comment.setUser(user);
        comment.setSong(song);
        comment.setText(request.getText().trim());
        comment.setStatus(CommentStatus.PROCESSING);

        commentRepository.save(comment);

        return CommentMapper.toDto(comment);
    }

    public List<CommentDto> getPublicComments() {
        return commentRepository.findAllByDeletedFalseAndStatusOrderByCreatedAtDesc(CommentStatus.COMPLETED)
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    public List<CommentDto> getPublicCommentsBySong(UUID songId) {
        return commentRepository.findAllBySongIdAndDeletedFalseAndStatusOrderByCreatedAtDesc(songId, CommentStatus.COMPLETED)
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    public List<CommentDto> getAllAdminComments() {
        return commentRepository.findAllByDeletedFalseOrderByCreatedAtDesc()
                .stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    public CommentDto updateStatus(UUID id, UpdateCommentStatusRequest request) {
        CommentEntity comment = commentRepository.findById(id)
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        comment.setStatus(request.getStatus());
        commentRepository.save(comment);

        return CommentMapper.toDto(comment);
    }

    public void delete(UUID id) {
        CommentEntity comment = commentRepository.findById(id)
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        comment.setDeleted(true);
        commentRepository.save(comment);
    }
}