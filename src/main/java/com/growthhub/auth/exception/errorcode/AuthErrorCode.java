package com.growthhub.auth.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    TOKEN_NOT_VALID(HttpStatus.FORBIDDEN, "Refresh Token is Not Valid."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
