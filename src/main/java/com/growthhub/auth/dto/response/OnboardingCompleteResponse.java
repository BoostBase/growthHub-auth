package com.growthhub.auth.dto.response;

import com.growthhub.auth.domain.type.Role;

public record OnboardingCompleteResponse(
        Long userId,
        Role role
) {
}
