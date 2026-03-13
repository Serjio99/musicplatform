package com.musicplatform.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminDashboardDto {

    private long totalUsers;
    private long totalSongs;
    private long totalComments;
    private long totalRequests;
    private long totalPayments;

    private long processingComments;
    private long createdRequests;
    private long waitingPayments;
}