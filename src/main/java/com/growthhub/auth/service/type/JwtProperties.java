package com.growthhub.auth.service.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private String issuer;
    private Long accessTokenExpiration;
    private Long refreshTokenExpiration;
}