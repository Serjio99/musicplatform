package com.musicplatform.song.controller;

import com.musicplatform.common.dto.ApiResponse;
import com.musicplatform.song.dto.CreateSongRequest;
import com.musicplatform.song.dto.SongDto;
import com.musicplatform.song.dto.UpdateSongRequest;
import com.musicplatform.song.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping("/api/songs")
    public ApiResponse<List<SongDto>> getAllSongs() {
        return ApiResponse.success(songService.getAllPublicSongs());
    }

    @GetMapping("/api/songs/{id}")
    public ApiResponse<SongDto> getSong(@PathVariable UUID id) {
        return ApiResponse.success(songService.getSongById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/admin/songs")
    public ApiResponse<SongDto> createSong(@Valid @RequestBody CreateSongRequest request) {
        return ApiResponse.success(songService.createSong(request), "Song created successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/admin/songs/{id}")
    public ApiResponse<SongDto> updateSong(@PathVariable UUID id,
                                           @Valid @RequestBody UpdateSongRequest request) {
        return ApiResponse.success(songService.updateSong(id, request), "Song updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/admin/songs/{id}")
    public ApiResponse<Void> deleteSong(@PathVariable UUID id) {
        songService.deleteSong(id);
        return ApiResponse.success(null, "Song deleted successfully");
    }
}