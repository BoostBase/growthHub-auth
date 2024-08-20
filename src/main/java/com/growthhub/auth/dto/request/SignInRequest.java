package com.growthhub.auth.dto.request;

public record SignInRequest(
        String email,
        String password
) {
}
