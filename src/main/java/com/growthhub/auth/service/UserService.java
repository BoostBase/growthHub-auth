package com.growthhub.auth.service;

import com.growthhub.auth.domain.User;
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
}
