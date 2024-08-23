package com.growthhub.auth.dto.request;

import com.growthhub.auth.domain.User;
import com.growthhub.auth.domain.type.Provider;
import com.growthhub.auth.domain.type.Role;
import com.growthhub.auth.util.EnumValid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignUpRequest(
        @NotNull String email,
        @NotNull String name,
        @NotNull String password,
        @NotNull String nickname,
        @NotNull String part,
        @EnumValid(enumClass = Role.class) String role
) {
    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(Role.valueOf(role))
                .allowContact(false)
                .provider(Provider.SELF)
                .association(null)
                .part(part)
                .careerYear(0L)
                .isVerified(false)
                .profileImage(null)
                .build();
    }
}
