package com.growthhub.auth.service;

import static com.growthhub.auth.exception.errorcode.UserErrorCode.DUPLICATION_EMAIL;

import com.growthhub.auth.dto.request.SignInRequest;
import com.growthhub.auth.dto.request.SignUpRequest;
import com.growthhub.auth.dto.response.AccessTokenResponse;
import com.growthhub.auth.exception.DuplicatedEmailException;
import com.growthhub.auth.repository.UserRepository;
import com.growthhub.auth.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    public String testToken(Long userId) {
        return jwtTokenProvider.createAccessToken(userRepository.findById(userId).orElseThrow());
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new DuplicatedEmailException(DUPLICATION_EMAIL);
        }

        userRepository.save(signUpRequest.toUser(passwordEncoder));
    }

    @Transactional
    public AccessTokenResponse signIn(SignInRequest signUpRequest, HttpServletResponse response) {

        String accessToken = loginService.login(signUpRequest.email(), signUpRequest.password(), response);

        return AccessTokenResponse.from(accessToken);
    }

    public AccessTokenResponse reIssueToken(String refreshToken) {
        String accessToken = jwtTokenProvider.createAccessToken(jwtTokenProvider.getUser(refreshToken));

        return AccessTokenResponse.from(accessToken);
    }
}
