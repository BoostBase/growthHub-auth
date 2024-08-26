package com.growthhub.auth.controller;

import com.growthhub.auth.dto.ResponseTemplate;
import com.growthhub.auth.dto.request.SignInRequest;
import com.growthhub.auth.dto.request.SignUpRequest;
import com.growthhub.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "테스트용 access token 발급", description = "테스트용 access token 발급")
    @GetMapping("/test/login/{userId}")
    public String testToken(@PathVariable Long userId) {
        return authService.testToken(userId);
    }

    @Operation(summary = "자체 회원가입 진행", description = "자체 회원가입 진행")
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseTemplate<?>> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

        authService.signUp(signUpRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "로그인 진행", description = "access token은 response로 전달, refresh token은 쿠키로 전달")
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseTemplate<?>> signIn(
            @RequestBody SignInRequest signInRequest, HttpServletResponse response) {

        authService.signIn(signInRequest, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "access token 재발급", description = "access token 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ResponseTemplate<?>> reIssueToken(@CookieValue String refreshToken) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(authService.reIssueToken(refreshToken)));
    }
}