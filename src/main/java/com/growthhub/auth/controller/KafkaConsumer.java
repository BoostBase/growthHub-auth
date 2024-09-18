package com.growthhub.auth.controller;

import com.growthhub.auth.dto.response.OnboardingCompleteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test", groupId = "growth-hub-group1")
    public void consume(@Payload OnboardingCompleteDto message){
        log.info("user-id: {}", message.userId());
    }
}