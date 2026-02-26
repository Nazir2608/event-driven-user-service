package com.nazir.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class NotificationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void notifyAdmin(Long userId, String email) {
        String message = "New user registered: " + email;
        log.info("Admin notified for user {} with email {}", userId, email);
    }
}