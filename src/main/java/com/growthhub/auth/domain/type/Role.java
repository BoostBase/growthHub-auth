package com.growthhub.auth.domain.type;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    MENTOR("ROLE_MENTOR"),
    MENTEE ("ROLE_MENTEE"),
    ;

    private final String key;

    Role(String key) {
        this.key = key;
    }
}
