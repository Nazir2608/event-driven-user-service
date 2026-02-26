package com.nazir.userservice.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisteredEvent {
    private Long userId;
    private String email;
}