package com.musicplatform.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank
    private String name;

    private String phone;

    private String organizationName;

    private String inn;

    private String ogrn;

}