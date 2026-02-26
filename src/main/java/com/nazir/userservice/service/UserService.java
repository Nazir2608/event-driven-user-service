package com.nazir.userservice.service;

import com.nazir.userservice.dto.UserRequest;
import com.nazir.userservice.dto.UserResponse;
import com.nazir.userservice.entity.User;
import com.nazir.userservice.event.UserRegisteredEvent;
import com.nazir.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;

    public UserResponse register(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        User saved = userRepository.save(user);

        publisher.publishEvent(new UserRegisteredEvent(saved.getId(), saved.getEmail()));

        return UserResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .build();
    }
}