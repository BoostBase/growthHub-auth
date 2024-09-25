package com.growthhub.auth.service;

import com.growthhub.auth.domain.User;
import com.growthhub.auth.dto.response.MentorResponse;
import com.growthhub.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MentorService {

    private final UserRepository userRepository;

    public List<MentorResponse> getMentors(List<Long> mentorIds) {
        List<User> mentors = userRepository.findByIds(mentorIds);
        return mentors.stream().map(MentorResponse::from).toList();
    }
}
