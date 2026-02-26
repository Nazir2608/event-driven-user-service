package com.nazir.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nazir.userservice.dto.UserRequest;
import com.nazir.userservice.dto.UserResponse;
import com.nazir.userservice.entity.OutboxEvent;
import com.nazir.userservice.entity.User;
import com.nazir.userservice.event.UserRegisteredEvent;
import com.nazir.userservice.repository.OutboxEventRepository;
import com.nazir.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public UserResponse register(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        // 1. Save User
        User user = User.builder().name(request.getName()).email(request.getEmail()).build();
        User saved = userRepository.save(user);
        // 2. Create Outbox Event (same transaction)
        OutboxEvent event = OutboxEvent.builder()
                .aggregateType("USER")
                .aggregateId(saved.getId())
                .eventType("USER_REGISTERED")
                .payload(convertToJson(saved))
                .status("PENDING")
                .createdAt(Instant.now())
                .build();

        outboxEventRepository.save(event);
        // 3. Return response
        return mapToResponse(saved);
    }

    private String convertToJson(User user) {
        try {
            UserRegisteredEvent event = new UserRegisteredEvent(user.getId(), user.getEmail());
          return   objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event payload", e);
        }
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}