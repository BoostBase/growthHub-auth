package com.growthhub.auth.controller;

import com.growthhub.auth.dto.response.OnboardingCompleteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "onboarding-ok", groupId = "auth-service")
    public void consume(@Payload OnboardingCompleteResponse message){
        log.info("user-id: {}", message.userId());
    }
}