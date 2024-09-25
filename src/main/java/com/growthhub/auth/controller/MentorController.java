package com.growthhub.auth.controller;

import com.growthhub.auth.dto.response.MentorResponse;
import com.growthhub.auth.service.MentorService;
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
public class MentorController {

    private final MentorService mentorService;

    @GetMapping("/mentors")
    public ResponseEntity<List<MentorResponse>> getMentors(
            @RequestParam("mentorIds") List<Long> mentorIds
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mentorService.getMentors(mentorIds));
    }
}
