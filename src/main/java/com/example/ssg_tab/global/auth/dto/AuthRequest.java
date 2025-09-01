package com.example.ssg_tab.global.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequest {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(title = "카카오 로그인 요청")
    public static class KakaoLoginRequest {

        @Schema(description = "카카오에서 발급받은 액세스 토큰")
        @NotBlank(message = "카카오 액세스 토큰을 필수로 입력해주세요.")
        private String kakaoAccessToken;

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(title = "토큰 재발급 요청")
    public static class RefreshTokenRequest {

        @Schema(description = "로그인 시 서버에서 발급해준 리프레쉬 토큰")
        @NotBlank(message = "리프레쉬 토큰을 필수로 입력해주세요.")
        private String refreshToken;
    }
}
