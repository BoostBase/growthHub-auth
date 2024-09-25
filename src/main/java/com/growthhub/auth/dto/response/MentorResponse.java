package com.growthhub.auth.dto.response;

import com.growthhub.auth.domain.User;
import lombok.Builder;

@Builder
public record MentorResponse(
        Long mentorId,
        String name,
        String nickname,
        String profileImageUrl,
        Long careerYear,
        String association,
        String part
) {

    public static MentorResponse from(User user) {
        return MentorResponse.builder()
                .mentorId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImage())
                .careerYear(user.getCareerYear())
                .association(user.getAssociation())
                .part(user.getPart())
                .build();
    }
}
