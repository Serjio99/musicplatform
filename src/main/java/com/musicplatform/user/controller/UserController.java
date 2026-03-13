package com.musicplatform.user.controller;

import com.musicplatform.common.dto.ApiResponse;
import com.musicplatform.user.dto.UpdateUserRequest;
import com.musicplatform.user.dto.UserDto;
import com.musicplatform.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ApiResponse<UserDto> getUser(@PathVariable UUID id) {

        return ApiResponse.success(userService.getUser(id));

    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request
    ) {

        return ApiResponse.success(userService.updateUser(id, request));

    }

}