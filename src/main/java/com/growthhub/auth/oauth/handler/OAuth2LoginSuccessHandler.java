package com.growthhub.auth.oauth.handler;

import com.growthhub.auth.oauth.dto.CustomOAuth2User;
import com.growthhub.auth.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.successRedirectUri}")
    private String redirectUri;
    private final JwtTokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String targetUrl = determineTargetUrl(response, oAuth2User);

        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


    private String determineTargetUrl(HttpServletResponse response, CustomOAuth2User oAuth2User) {

        // JWT 생성
        String accessToken = tokenProvider.createAccessToken(oAuth2User);
        tokenProvider.createRefreshToken(oAuth2User, response);

        response.setHeader("Authorization", accessToken);

        return UriComponentsBuilder.fromUriString(redirectUri)
                .build().toUriString();
    }
}