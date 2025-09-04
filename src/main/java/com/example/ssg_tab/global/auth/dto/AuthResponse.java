package com.example.ssg_tab.global.auth.dto;

import com.example.ssg_tab.domain.user.entity.enums.UserStep;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponse {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(title = "로그인 응답")
    public static class LoginResponse {

        @Schema(description = "로그인 유저의 다음 단계(온보딩 or 메인화면)")
        private UserStep step;

        @Schema(description = "서버 발급 액세스 토큰")
        private String accessToken;

        @Schema(description = "서버 발급 리프레쉬 토큰")
        private String refreshToken;

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(title = "회원가입 응답")
    public static class SignUpResponse {

        @Schema(description = "로그인 유저의 다음 단계(온보딩 or 메인화면)")
        private UserStep step;

        @Schema(description = "서버 발급 액세스 토큰")
        private String accessToken;

        @Schema(description = "서버 발급 리프레쉬 토큰")
        private String refreshToken;

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(title = "토큰 재발급 응답")
    public static class ReissueResponse {

        @Schema(description = "재발급된 액세스 토큰")
        private String accessToken;

        @Schema(description = "재발급된 리프레쉬 토큰")
        private String refreshToken;
    }

}
