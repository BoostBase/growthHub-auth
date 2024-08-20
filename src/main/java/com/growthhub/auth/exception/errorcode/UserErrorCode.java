package com.growthhub.auth.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found."),
    DUPLICATION_EMAIL(HttpStatus.BAD_REQUEST, "Email already exists.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
