package com.growthhub.auth.dto.response;

import lombok.Builder;

@Builder
public record MentorOnboardingKafkaResponse(
        Long userId,
        String association,
        String part,
        Long careerYear
) {
}
