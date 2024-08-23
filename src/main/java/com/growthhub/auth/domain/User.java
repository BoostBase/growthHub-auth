package com.growthhub.auth.domain;

import com.growthhub.auth.domain.type.Provider;
import com.growthhub.auth.domain.type.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "nickname", length = 30, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "allow_contact", nullable = false)
    private Boolean allowContact;

    @Column(name = "career_year", nullable = false)
    private Long careerYear;

    @Column(name = "association")
    private String association;

    @Column(name = "part", nullable = false)
    private String part;

    @Builder
    public User(String email, String name, String password, String nickname, Role role, Provider provider,
                String socialId, Boolean isVerified, String profileImage, Boolean allowContact, Long careerYear,
                String association, String part) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
        this.socialId = socialId;
        this.isVerified = isVerified;
        this.profileImage = profileImage;
        this.allowContact = allowContact;
        this.careerYear = careerYear;
        this.association = association;
        this.part = part;
    }
}
