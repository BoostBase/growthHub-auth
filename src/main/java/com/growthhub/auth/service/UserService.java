package com.growthhub.auth.service;

import com.growthhub.auth.domain.User;
import com.growthhub.auth.domain.type.Role;
import com.growthhub.auth.dto.response.OnboardingCompleteResponse;
import com.growthhub.auth.dto.response.UserResponse;
import com.growthhub.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getUsers(List<Long> userIds) {
        List<User> users = userRepository.findByIds(userIds);
        return users.stream().map(UserResponse::from).toList();
    }

    @Transactional
    public void menteeOnboarding(Long userId, Role role) {
        userRepository.updateUserByIsOnboarded(userId, role);
    }

    @Transactional
    public void mentorOnboarding(Long userId, String association, String part, Long careerYear) {
        userRepository.updateMentorWithOnboarding(userId, association, part, careerYear);
    }
}
