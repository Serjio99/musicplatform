package com.musicplatform.user.service;

import com.musicplatform.common.exception.NotFoundException;
import com.musicplatform.user.dto.UpdateUserRequest;
import com.musicplatform.user.dto.UserDto;
import com.musicplatform.user.entity.UserEntity;
import com.musicplatform.user.mapper.UserMapper;
import com.musicplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUser(UUID userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return UserMapper.toDto(user);
    }

    public UserDto updateUser(UUID userId, UpdateUserRequest request) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setOrganizationName(request.getOrganizationName());
        user.setInn(request.getInn());
        user.setOgrn(request.getOgrn());

        userRepository.save(user);

        return UserMapper.toDto(user);
    }

}