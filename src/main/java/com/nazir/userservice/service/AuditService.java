package com.nazir.userservice.audit.service;

import com.nazir.userservice.entity.AuditLog;
import com.nazir.userservice.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void logUserRegistration(Long userId, String email) {

        AuditLog logEntry = AuditLog.builder()
                .action("USER_REGISTERED")
                .referenceId(userId)
                .description("User registered with email: " + email)
                .build();

        auditLogRepository.save(logEntry);

        log.info("Audit log saved for user {}", userId);
    }
}