package com.growthhub.auth.service;

import static com.growthhub.auth.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.growthhub.auth.domain.User;
import com.growthhub.auth.exception.UserNotFoundException;
import com.growthhub.auth.repository.UserRepository;
import com.growthhub.auth.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String login(String email, String password, HttpServletResponse response) {

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }

        //토큰 생성 후 리턴
        String accessToken = jwtTokenProvider.createAccessToken(user);
        setRefreshToken(response, user);

        return accessToken;
    }

    private void setRefreshToken(HttpServletResponse response, User user) {
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(14 * 24 * 60 * 60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());
    }
}
