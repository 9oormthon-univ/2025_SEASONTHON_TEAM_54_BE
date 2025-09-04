package com.example.ssg_tab.domain.user.dto.request;

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

public class UserRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "온보딩 입력값")
    public static class OnboardingRequest {

        @Schema(description = "사용자 닉네임 - 이메일 회원가입 유저 사용")
        private String nickname;

        @Schema(description = "프로필 이미지 url - 이메일 회원가입 유저 사용")
        private String profileImageUrl;

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
