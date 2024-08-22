package com.growthhub.auth.oauth.dto.response;


import com.growthhub.auth.domain.type.Provider;

public interface OAuth2UserInfo {

    String getSocialId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"
    String getNickname();
    String getProfileImage();
    String getName();
    String getEmail();
    Provider getOAuthProvider();
}