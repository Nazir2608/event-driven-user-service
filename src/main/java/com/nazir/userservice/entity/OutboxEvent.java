package com.nazir.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "outbox_events",indexes = {@Index(name = "idx_outbox_status", columnList = "status")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String aggregateType; // USER
    private Long aggregateId;
    private String eventType; // USER_REGISTERED
    @Lob
    private String payload; // JSON event data
    private String status; // PENDING, PROCESSED, FAILED
    private Instant createdAt;
}
