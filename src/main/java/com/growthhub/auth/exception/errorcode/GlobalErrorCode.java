package com.growthhub.auth.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Dto @Valid 관련 error 해결을 위한 enum class
 */
@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "You don't have permission to access this resource"),
    INVALID_METHOD(HttpStatus.METHOD_NOT_ALLOWED, "Invalid Method"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}