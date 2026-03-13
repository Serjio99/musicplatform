package com.musicplatform.user.mapper;

import com.musicplatform.user.dto.UserDto;
import com.musicplatform.user.entity.UserEntity;

public class UserMapper {

    public static UserDto toDto(UserEntity entity) {

        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .organizationName(entity.getOrganizationName())
                .inn(entity.getInn())
                .ogrn(entity.getOgrn())
                .build();

    }

}