package com.growthhub.auth.controller;

import com.growthhub.auth.domain.type.Role;
import com.growthhub.auth.dto.ResponseTemplate;
import com.growthhub.auth.dto.response.OnboardingCompleteResponse;
import com.growthhub.auth.dto.response.UserResponse;
import com.growthhub.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers(
            @RequestParam("userIds") List<Long> userIds
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsers(userIds));
    }

    @GetMapping("/mentee-onboarding")
    public ResponseEntity<?> menteeOnboarding(
            @RequestParam Long userId,
            @RequestParam Role role
            ) {
        log.info("user-id: {}", userId);
        userService.menteeOnboarding(userId, role);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Void.TYPE);
    }

    @GetMapping("/mentor-onboarding")
    public ResponseEntity<?> mentorOnboarding(
            @RequestParam Long userId,
            @RequestParam String association,
            @RequestParam String part,
            @RequestParam Long careerYear
    ) {
        log.info("user-id: {}", userId);
        userService.mentorOnboarding(userId, association, part, careerYear);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Void.TYPE);
    }
}
