package com.example.ssg_tab.global.auth.dto;

import com.example.ssg_tab.domain.user.entity.enums.AgeBand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

        @Schema(description = "연령대 ENUM")
        @NotNull
        private AgeBand ageBand;

        @Schema(description = "거주지역")
        @NotBlank
        private String region;

        @Schema(description = "직업군")
        @NotBlank
        private String job;

        @Schema(description = "관심분야 카테고리 id값 리스트(3~5개 선택)")
        @NotNull
        @Size(min = 3, max = 5)
        private List<Long> categoryIds;

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

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(title = "이메일로 회원가입 요청")
    public static class EmailSignUpRequest {

        @Schema(description = "사용자 닉네임")
        @NotBlank
        private String nickname;

        @Schema(description = "회원가입할 이메일")
        @NotBlank
        private String email;

        @Schema(description = "회원가입할 비밀번호")
        @NotBlank
        private String password;

        @Schema(description = "연령대 ENUM")
        @NotNull
        private AgeBand ageBand;

        @Schema(description = "거주지역")
        @NotBlank
        private String region;

        @Schema(description = "직업군")
        @NotBlank
        private String job;

        @Schema(description = "관심분야 카테고리 id값 리스트(3~5개 선택)")
        @NotNull
        @Size(min = 3, max = 5)
        private List<Long> categoryIds;
    }
}
