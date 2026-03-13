package com.musicplatform.request.service;

import com.musicplatform.common.exception.NotFoundException;
import com.musicplatform.request.dto.CreateRequestRequest;
import com.musicplatform.request.dto.RequestDto;
import com.musicplatform.request.dto.UpdateRequestStatusRequest;
import com.musicplatform.request.entity.LicenseType;
import com.musicplatform.request.entity.RequestEntity;
import com.musicplatform.request.mapper.RequestMapper;
import com.musicplatform.request.repository.RequestRepository;
import com.musicplatform.security.SecurityUtils;
import com.musicplatform.song.entity.SongEntity;
import com.musicplatform.song.repository.SongRepository;
import com.musicplatform.user.entity.UserEntity;
import com.musicplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public RequestDto create(CreateRequestRequest request) {

        UUID userId = SecurityUtils.getCurrentUserId();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        SongEntity song = songRepository.findById(request.getSongId())
                .orElseThrow(() -> new NotFoundException("Song not found"));

        RequestEntity entity = new RequestEntity();

        entity.setId(UUID.randomUUID());
        entity.setUser(user);
        entity.setSong(song);
        entity.setLicenseType(request.getLicenseType());
        entity.setPrice(resolvePrice(song, request.getLicenseType()));
        entity.setComment(request.getComment());

        requestRepository.save(entity);

        return RequestMapper.toDto(entity);
    }

    public List<RequestDto> getMyRequests() {

        UUID userId = SecurityUtils.getCurrentUserId();

        return requestRepository.findAllByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(RequestMapper::toDto)
                .toList();
    }

    public List<RequestDto> getAllAdmin() {

        return requestRepository.findAllByDeletedFalseOrderByCreatedAtDesc()
                .stream()
                .map(RequestMapper::toDto)
                .toList();
    }

    public RequestDto updateStatus(UUID id, UpdateRequestStatusRequest request) {

        RequestEntity entity = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Request not found"));

        entity.setStatus(request.getStatus());

        requestRepository.save(entity);

        return RequestMapper.toDto(entity);
    }

    private BigDecimal resolvePrice(SongEntity song, LicenseType licenseType) {

        return switch (licenseType) {

            case ECONOMY -> song.getEconomyPrice();
            case STANDARD -> song.getStandardPrice();
            case BUSINESS -> song.getBusinessPrice();
            case PREMIUM -> song.getPremiumPrice();
            case FULL_LICENSE -> song.getLicensePrice();

        };

    }

}