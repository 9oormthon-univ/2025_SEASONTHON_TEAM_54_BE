package com.example.ssg_tab.global.auth.converter;

import com.example.ssg_tab.global.auth.dto.AuthResponse;
import com.example.ssg_tab.domain.user.entity.User;

public class AuthConverter {

    // 로그인 응답값 생성
    public static AuthResponse.LoginResponse toLoginResponse(String accessToken, String refreshToken) {
        return AuthResponse.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 회원가입 응답값 생성
    public static AuthResponse.SignUpResponse toSignUpResponse(User user) {

        return AuthResponse.SignUpResponse.builder()
                .userId(user.getId())
                .build();
    }

}
