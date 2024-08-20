package com.growthhub.auth.exception;

import com.growthhub.auth.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicatedEmailException extends RuntimeException {
    private final ErrorCode errorCode;
}
