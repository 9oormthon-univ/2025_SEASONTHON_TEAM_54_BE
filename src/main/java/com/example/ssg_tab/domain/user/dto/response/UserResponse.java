package com.example.ssg_tab.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

public class UserResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "사용자 정보 조회")
    public static class UserInfoResponse {

        @Schema(description = "유저 ID")
        private Long id;

        @Schema(description = "유저 닉네임")
        private String nickname;

        @Schema(description = "유저 이메일")
        private String email;

        @Schema(description = "유저 프로필 이미지")
        private String profileImageUrl;

    }

}
