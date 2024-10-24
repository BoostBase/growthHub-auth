//package com.growthhub.auth.service;
//
//import com.growthhub.auth.dto.response.MentorOnboardingKafkaResponse;
//import com.growthhub.auth.dto.response.OnboardingCompleteResponse;
//import com.growthhub.auth.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class KafkaConsumer {
//
//    private final UserRepository userRepository;
//
//    @Transactional
//    //@KafkaListener(topics = "onboarding-mentee-ok", groupId = "auth-service")
//    public void consumeMenteeOnboarding(@Payload OnboardingCompleteResponse message) {
//        log.info("user-id: {}", message.userId());
//        userRepository.updateUserByIsOnboarded(message.userId(), message.role());
//    }
//
//    @Transactional
//    //@KafkaListener(topics = "onboarding-mentor-ok", groupId = "auth-service")
//    public void consumeMentorOnboarding(@Payload MentorOnboardingKafkaResponse message) {
//        log.info("user-id: {}", message.userId());
//        userRepository.updateMentorWithOnboarding(
//                message.userId(), message.association(), message.part(), message.careerYear());
//    }
//}