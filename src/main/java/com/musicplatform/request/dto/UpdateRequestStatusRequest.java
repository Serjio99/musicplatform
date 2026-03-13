package com.musicplatform.request.dto;

import com.musicplatform.request.entity.RequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestStatusRequest {

    @NotNull
    private RequestStatus status;

}