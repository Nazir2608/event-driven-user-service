package com.nazir.userservice.listener;
import com.nazir.userservice.event.UserRegisteredEvent;
import com.nazir.userservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventConsumer {

    @Autowired
    com.nazir.userservice.audit.service.AuditService auditService;

    @Autowired
    NotificationService notificationService;

    @KafkaListener(topics = "user-events", groupId = "email-group")
    public void consume(UserRegisteredEvent event) {
        log.info("Received event from Kafka for user {}", event.getEmail());
        auditService.logUserRegistration(event.getUserId(), event.getEmail());
        notificationService.notifyAdmin(event.getUserId(), event.getEmail());
        // Call EmailService

    }
}