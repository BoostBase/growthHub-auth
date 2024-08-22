package com.growthhub.auth.domain.type;

import lombok.Getter;

@Getter
public enum Provider {
    KAKAO("kakao"),
    NAVER("naver"),
    SELF("self")
    ;

    private final String type;

    Provider(String type) {
        this.type = type;
    }
}
