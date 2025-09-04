package com.example.ssg_tab.global.auth.converter;

import com.example.ssg_tab.global.auth.dto.AuthResponse;
import com.example.ssg_tab.domain.user.entity.User;

public class AuthConverter {

    // 로그인 응답값 생성
    public static AuthResponse.LoginResponse toLoginResponse(User user, String accessToken, String refreshToken) {
        return AuthResponse.LoginResponse.builder()
                .step(user.getStep())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 토큰 재발급 응답값 생성
    public static AuthResponse.ReissueResponse toReissueResponse(String accessToken, String refreshToken) {
        return AuthResponse.ReissueResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 회원가입 응답값 생성
    public static AuthResponse.SignUpResponse toSignUpResponse(User user, String accessToken, String refreshToken) {
        return AuthResponse.SignUpResponse.builder()
                .step(user.getStep())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
