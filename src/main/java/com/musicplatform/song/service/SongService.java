package com.musicplatform.song.service;

import com.musicplatform.common.exception.NotFoundException;
import com.musicplatform.song.dto.CreateSongRequest;
import com.musicplatform.song.dto.SongDto;
import com.musicplatform.song.dto.UpdateSongRequest;
import com.musicplatform.song.entity.SongEntity;
import com.musicplatform.song.mapper.SongMapper;
import com.musicplatform.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public List<SongDto> getAllPublicSongs() {
        return songRepository.findAllByDeletedFalseOrderByCreatedAtDesc()
                .stream()
                .map(SongMapper::toDto)
                .toList();
    }

    public SongDto getSongById(UUID id) {
        SongEntity song = songRepository.findById(id)
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(() -> new NotFoundException("Song not found"));

        return SongMapper.toDto(song);
    }

    public SongDto createSong(CreateSongRequest request) {
        SongEntity song = new SongEntity();
        song.setId(UUID.randomUUID());
        apply(song, request);

        songRepository.save(song);

        return SongMapper.toDto(song);
    }

    public SongDto updateSong(UUID id, UpdateSongRequest request) {
        SongEntity song = songRepository.findById(id)
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(() -> new NotFoundException("Song not found"));

        apply(song, request);
        songRepository.save(song);

        return SongMapper.toDto(song);
    }

    public void deleteSong(UUID id) {
        SongEntity song = songRepository.findById(id)
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(() -> new NotFoundException("Song not found"));

        song.setDeleted(true);
        songRepository.save(song);
    }

    private void apply(SongEntity song, CreateSongRequest request) {
        song.setName(request.getName().trim());
        song.setDescription(request.getDescription());
        song.setImageFileId(request.getImageFileId());
        song.setAudioFileId(request.getAudioFileId());
        song.setLicensePrice(request.getLicensePrice());
        song.setEconomyPrice(request.getEconomyPrice());
        song.setStandardPrice(request.getStandardPrice());
        song.setBusinessPrice(request.getBusinessPrice());
        song.setPremiumPrice(request.getPremiumPrice());
    }

    private void apply(SongEntity song, UpdateSongRequest request) {
        song.setName(request.getName().trim());
        song.setDescription(request.getDescription());
        song.setImageFileId(request.getImageFileId());
        song.setAudioFileId(request.getAudioFileId());
        song.setLicensePrice(request.getLicensePrice());
        song.setEconomyPrice(request.getEconomyPrice());
        song.setStandardPrice(request.getStandardPrice());
        song.setBusinessPrice(request.getBusinessPrice());
        song.setPremiumPrice(request.getPremiumPrice());
    }
}