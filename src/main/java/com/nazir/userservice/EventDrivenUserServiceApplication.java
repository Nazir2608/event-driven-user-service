package com.nazir.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EventDrivenUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventDrivenUserServiceApplication.class, args);
    }
}
