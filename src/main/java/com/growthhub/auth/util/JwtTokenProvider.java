package com.growthhub.auth.util;

import static com.growthhub.auth.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.growthhub.auth.domain.User;
import com.growthhub.auth.exception.UserNotFoundException;
import com.growthhub.auth.oauth.dto.CustomOAuth2User;
import com.growthhub.auth.repository.UserRepository;
import com.growthhub.auth.service.type.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    private Key key;
    private static final String USER_ROLE = "role";

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(Object user) {
        return createToken(user, jwtProperties.accessTokenExpiration());
    }

    /**
     * RefreshToken 생성
     */
    public void createRefreshToken(Object user, HttpServletResponse response) {
        String refreshToken = createToken(user, jwtProperties.refreshTokenExpiration());

        ResponseCookie cookie = ResponseCookie.from("REFRESH_TOKEN", refreshToken)
                .maxAge(jwtProperties.refreshTokenExpiration() / 1000)
                .path("/")
                .secure(true)
                .sameSite("Lax")
                .httpOnly(true)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    /**
     * JWT 토큰 생성 메소드
     */
    private String createToken(Object user, long expiration) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + expiration);

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .setIssuer(jwtProperties.issuer())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512);

        if (user instanceof User regularUser) {
            builder.setSubject(regularUser.getId().toString())
                    .addClaims(Map.of(USER_ROLE, regularUser.getRole().name()));
        } else if (user instanceof CustomOAuth2User oAuth2User) {
            builder.setSubject(oAuth2User.getUserId().toString())
                    .addClaims(Map.of(USER_ROLE, oAuth2User.getAuthorities()));
        } else {
            throw new IllegalArgumentException("Unsupported user type: " + user.getClass().getName());
        }

        return builder.compact();
    }


    public boolean validateToken(final String token) {
        try {
            log.info("now date: {}", new Date());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            log.info("expire date: {}", claims.getBody().getExpiration());
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("Token validation error: ", e);
            return false;
        }
    }

    public User getUser(String token) {
        Long id = Long.parseLong(Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject());

        log.info("in getUser() id: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}