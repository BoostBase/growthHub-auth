package com.growthhub.auth.repository.init;


import com.growthhub.auth.domain.User;
import com.growthhub.auth.domain.type.Provider;
import com.growthhub.auth.domain.type.Role;
import com.growthhub.auth.repository.UserRepository;
import com.growthhub.auth.util.DummyDataInit;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {
            List<User> userList = new ArrayList<>();

            User DUMMY_ADMIN = User.builder()
                    .email("admin@naver.com")
                    .name("관리자")
                    .association("Alpha Motorsports")
                    .isVerified(true)
                    .allowContact(true)
                    .role(Role.ADMIN)
                    .careerYear(0L)
                    .profileImage("https://growth-hub.s3.ap-northeast-2.amazonaws.com/b3d25ef6-4648-4e2b-854d-f40e5ceb4e16.png")
                    .provider(Provider.SELF)
                    .password(passwordEncoder.encode("adminPassword"))
                    .nickname("관리자")
                    .part("관리자")
                    .build();

            User DUMMY_MENTOR1 = User.builder()
                    .email("mentor1@naver.com")
                    .name("이상호")
                    .association("Shadow Racers")
                    .isVerified(true)
                    .allowContact(true)
                    .role(Role.MENTOR)
                    .careerYear(4L)
                    .profileImage("https://growth-hub.s3.ap-northeast-2.amazonaws.com/pngtree-nerd-face-emoji-png-image_11469701.png")
                    .provider(Provider.SELF)
                    .password(passwordEncoder.encode("mentorPassword"))
                    .nickname("DB 킹")
                    .part("데이터베이스")
                    .build();
            User DUMMY_MENTOR2 = User.builder()
                    .email("mentor2@gmail.com")
                    .name("홍지만")
                    .association("TurboAce")
                    .isVerified(true)
                    .allowContact(true)
                    .role(Role.MENTOR)
                    .careerYear(4L)
                    .profileImage("https://growth-hub.s3.ap-northeast-2.amazonaws.com/b3d25ef6-4648-4e2b-854d-f40e5ceb4e16.png")
                    .provider(Provider.SELF)
                    .password(passwordEncoder.encode("mentorPassword"))
                    .nickname("홍박사")
                    .part("OS")
                    .build();
            User DUMMY_MENTOR3 = User.builder()
                    .email("mentor3@gmail.com")
                    .name("김석윤")
                    .association("IronHorse")
                    .isVerified(true)
                    .allowContact(true)
                    .role(Role.MENTOR)
                    .careerYear(0L)
                    .profileImage("https://growth-hub.s3.ap-northeast-2.amazonaws.com/b3d25ef6-4648-4e2b-854d-f40e5ceb4e16.png")
                    .provider(Provider.SELF)
                    .password(passwordEncoder.encode("mentorPassword"))
                    .nickname("명퇴자")
                    .part("전자 회로")
                    .build();
            User DUMMY_MENTOR4 = User.builder()
                    .email("mentor4@gmail.com")
                    .name("김철홍")
                    .association("Lightning Speed Team")
                    .isVerified(true)
                    .allowContact(true)
                    .role(Role.MENTOR)
                    .careerYear(0L)
                    .profileImage("https://growth-hub.s3.ap-northeast-2.amazonaws.com/nerd-face-emoji-on-white-background-high-quality-4k-hdr-free-photo.jpg")
                    .provider(Provider.SELF)
                    .password(passwordEncoder.encode("mentorPassword"))
                    .nickname("멘토 김")
                    .part("OS")
                    .build();
            User DUMMY_MENTOR5 = User.builder()
                    .email("mentor5@gmail.com")
                    .name("김익수")
                    .association("IronHorse")
                    .isVerified(true)
                    .allowContact(true)
                    .role(Role.MENTOR)
                    .careerYear(1L)
                    .profileImage("https://growth-hub.s3.ap-northeast-2.amazonaws.com/b3d25ef6-4648-4e2b-854d-f40e5ceb4e16.png")
                    .provider(Provider.SELF)
                    .password(passwordEncoder.encode("mentorPassword"))
                    .nickname("익수플로러")
                    .part("C언어")
                    .build();

            User DUMMY_MENTEE = User.builder()
                    .email("mentee@naver.com")
                    .name("멘티티")
                    .role(Role.MENTEE)
                    .careerYear(0L)
                    .isVerified(true)
                    .allowContact(true)
                    .profileImage("https://growth-hub.s3.ap-northeast-2.amazonaws.com/nerd-face-emoji-on-white-background-high-quality-4k-hdr-free-photo.jpg")
                    .provider(Provider.SELF)
                    .password(passwordEncoder.encode("menteePassword"))
                    .nickname("백엔드 지망생")
                    .part("백엔드")
                    .build();

            userList.add(DUMMY_ADMIN);
            userList.add(DUMMY_MENTOR1);
            userList.add(DUMMY_MENTOR2);
            userList.add(DUMMY_MENTOR3);
            userList.add(DUMMY_MENTOR4);
            userList.add(DUMMY_MENTOR5);
            userList.add(DUMMY_MENTEE);

            userRepository.saveAll(userList);
        }
    }
}
