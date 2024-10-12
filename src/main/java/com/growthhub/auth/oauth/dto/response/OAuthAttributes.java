package com.growthhub.auth.oauth.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.growthhub.auth.domain.User;
import com.growthhub.auth.domain.type.Provider;
import com.growthhub.auth.domain.type.Role;
import com.growthhub.auth.oauth.dto.response.KakaoOAuth2UserInfo.KakaoAccount;
import com.growthhub.auth.oauth.dto.response.NaverOAuth2UserInfo.Response;
import java.util.Map;
import lombok.Builder;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Builder
public record OAuthAttributes(
        String nameAttributeKey,        //OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
        OAuth2UserInfo oauth2UserInfo   //소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)
) {

    public static OAuthAttributes of(
            Provider provider, String userNameAttributeName, Map<String, Object> attributes) {

        if (provider == Provider.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        } else if (provider == Provider.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        } else {
            throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoAccount kakaoAccount = objectMapper.convertValue(attributes.get("kakao_account"), KakaoAccount.class);

        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes.get("id").toString(), kakaoAccount))
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        ObjectMapper objectMapper = new ObjectMapper();
        Response response = objectMapper.convertValue(attributes.get("response"), Response.class);

        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(response))
                .build();
    }

    /**
     * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태 OAuth2UserInfo에서 socialId(식별값),
     */
    public User toEntity(OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .email(oauth2UserInfo.getEmail())
                .name(oauth2UserInfo.getName())
                .isVerified(false)
                .allowContact(false)
                .role(Role.MENTEE)
                .careerYear(0L)
                .profileImage(oauth2UserInfo.getProfileImage())
                .provider(oauth2UserInfo.getOAuthProvider())
                .nickname(oauth2UserInfo.getNickname())
                .part("")
                .isOnboarded(false)
                .build();
    }
}