package com.growthhub.auth.service;

import static com.growthhub.auth.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.growthhub.auth.domain.User;
import com.growthhub.auth.exception.UserNotFoundException;
import com.growthhub.auth.repository.UserRepository;
import com.growthhub.auth.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    @Value("${app.onboarding.uri}")
    private String onboardingUri;

    private final UserRepository userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public void login(String email, String password, HttpServletResponse response) {

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }

        //토큰 생성 후 리턴
        String accessToken = jwtTokenProvider.createAccessToken(user);
        jwtTokenProvider.createRefreshToken(user, response);

        response.setHeader("Authorization", "Bearer " + accessToken);

        if (!user.getIsOnboarded()) {
            try {
                response.sendRedirect(onboardingUri); // 온보딩이 완료되지 않았다면 리다이렉트
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
