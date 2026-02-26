package com.nazir.userservice.scheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nazir.userservice.entity.OutboxEvent;
import com.nazir.userservice.event.KafkaEventPublisher;
import com.nazir.userservice.event.UserRegisteredEvent;
import com.nazir.userservice.repository.OutboxEventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxProcessor {

    private final OutboxEventRepository repository;
    private final ObjectMapper objectMapper;
    private final KafkaEventPublisher kafkaPublisher;


    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void processOutboxEvents() {
        List<OutboxEvent> events = repository.findByStatus("PENDING");
        for (OutboxEvent event : events) {
            try {
                // Convert JSON payload back to event object
                UserRegisteredEvent userEvent = objectMapper.readValue(event.getPayload(), UserRegisteredEvent.class);
                kafkaPublisher.publishAndWait(String.valueOf(event.getAggregateId()),userEvent);
                event.setStatus("PROCESSED");
            } catch (Exception e) {
                log.error("Failed to process event {}", event.getId());
                event.setStatus("FAILED");
            }
        }
    }
}