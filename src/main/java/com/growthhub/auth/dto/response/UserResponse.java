package com.growthhub.auth.dto.response;

import com.growthhub.auth.domain.User;
import lombok.Builder;

@Builder
public record UserResponse(
        Long userId,
        String name,
        String nickname,
        String profileImageUrl,
        Long careerYear,
        String association,
        String part
) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImage())
                .careerYear(user.getCareerYear())
                .association(user.getAssociation())
                .part(user.getPart())
                .build();
    }
}
