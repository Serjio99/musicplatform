package com.musicplatform.admin.service;

import com.musicplatform.admin.dto.AdminDashboardDto;
import com.musicplatform.comment.entity.CommentStatus;
import com.musicplatform.comment.repository.CommentRepository;
import com.musicplatform.payment.entity.PaymentStatus;
import com.musicplatform.payment.repository.PaymentRepository;
import com.musicplatform.request.entity.RequestStatus;
import com.musicplatform.request.repository.RequestRepository;
import com.musicplatform.song.repository.SongRepository;
import com.musicplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final CommentRepository commentRepository;
    private final RequestRepository requestRepository;
    private final PaymentRepository paymentRepository;

    public AdminDashboardDto getDashboard() {
        return AdminDashboardDto.builder()
                .totalUsers(userRepository.count())
                .totalSongs(songRepository.count())
                .totalComments(commentRepository.count())
                .totalRequests(requestRepository.count())
                .totalPayments(paymentRepository.count())
                .processingComments(commentRepository.countByStatus(CommentStatus.PROCESSING))
                .createdRequests(requestRepository.countByStatus(RequestStatus.CREATED))
                .waitingPayments(paymentRepository.countByStatus(PaymentStatus.WAITING_FOR_CONFIRMATION))
                .build();
    }
}