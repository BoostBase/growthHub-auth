package com.growthhub.auth.controller;

import com.growthhub.auth.dto.response.UserResponse;
import com.growthhub.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
