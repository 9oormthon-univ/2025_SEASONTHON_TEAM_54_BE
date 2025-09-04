package com.example.ssg_tab.global.auth.dto;

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

        @Schema(description = "생성된 사용자의 id값")
        private Long userId;

    }

}
