package com.nazir.userservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "user-events";

    public void publishAndWait(String key, Object event) throws Exception {
        kafkaTemplate.send(TOPIC, key, event).get();
        log.info("Published event to Kafka topic {}", TOPIC);
    }
}