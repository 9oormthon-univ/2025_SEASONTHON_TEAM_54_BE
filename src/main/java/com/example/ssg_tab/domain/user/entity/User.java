package com.example.ssg_tab.domain.user.entity;

import com.example.ssg_tab.domain.user.entity.enums.AgeBand;
import com.example.ssg_tab.domain.user.entity.enums.UserRole;
import com.example.ssg_tab.domain.user.entity.enums.UserStep;
import com.example.ssg_tab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long socialId;

    @Column(name = "nickname", length = 100)
    private String nickname;    // 닉네임(카카오 로그인 시 기본)

    @Column(name = "email", unique = true, length = 255)
    private String email;       // 이메일(카카오 로그인 시 기본)

    @Column(name = "profile_image_url")
    private String profileImageUrl; // 프로필 이미지(카카오 로그인 시 기본)

    @Enumerated(EnumType.STRING)
    @Column(name = "age", length = 20)
    private AgeBand ageBand;    // 연령대(20대 초, 중, 후, 30대)

    @Column(name = "region", length = 255)
    private String region;  //  거주지역

    @Column(name = "job", length = 100)
    private String job;     // 직업군

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "step", nullable = false)
    private UserStep step = UserStep.ONBOARDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Builder.Default
    @Column(name = "level", nullable = false)
    private int level = 1;  // 1부터 시작

    @Column(name = "password")
    private String password;

    /**
     * 편의 메소드
     **/
    public void encodePassword(String password) {
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateAgeBand(AgeBand ageBand) {
        this.ageBand = ageBand;
    }

    public void updateRegion(String region) {
        this.region = region;
    }

    public void updateJob(String job) {
        this.job = job;
    }

    public void updateStep(UserStep step) {
        this.step = step;
    }

}
