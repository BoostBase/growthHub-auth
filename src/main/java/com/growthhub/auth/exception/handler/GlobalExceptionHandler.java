package com.growthhub.auth.exception.handler;

import com.growthhub.auth.exception.DuplicatedEmailException;
import com.growthhub.auth.exception.TokenNotValidException;
import com.growthhub.auth.exception.UserNotFoundException;
import com.growthhub.auth.exception.errorcode.ErrorCode;
import com.growthhub.auth.exception.errorcode.GlobalErrorCode;
import com.growthhub.auth.exception.response.ErrorResponse;
import com.growthhub.auth.exception.response.ErrorResponse.ValidationError;
import com.growthhub.auth.exception.response.ErrorResponse.ValidationErrors;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<Object> handleTokenNotValid(TokenNotValidException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<Object> handleDuplicationEmail(DuplicatedEmailException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        return handleExceptionInternal(GlobalErrorCode.INVALID_PARAMETER, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        return handleExceptionInternal(GlobalErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * DTO @Valid 관련 exception 처리
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException e,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return handleExceptionInternal(e);
    }

    /**
     * HTTP Method 관련 exception 처리
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NonNull HttpRequestMethodNotSupportedException e,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return handleExceptionInternal(GlobalErrorCode.INVALID_METHOD);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .results(new ValidationErrors(null))
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(message)
                .results(new ValidationErrors(null))
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e) {
        return ResponseEntity.status(GlobalErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(makeErrorResponse(e));
    }

    private ErrorResponse makeErrorResponse(BindException e) {
        final List<ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::from)
                .toList();

        return ErrorResponse.builder()
                .isSuccess(false)
                .code(GlobalErrorCode.INVALID_PARAMETER.name())
                .message(GlobalErrorCode.INVALID_PARAMETER.getMessage())
                .results(new ValidationErrors(validationErrorList))
                .build();
    }
}
