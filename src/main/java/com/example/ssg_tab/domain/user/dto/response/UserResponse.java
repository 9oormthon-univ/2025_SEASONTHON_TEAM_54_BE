package com.example.ssg_tab.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "사용자 정보 조회")
    public static class UserInfo {

        @Schema(description = "유저 ID")
        private Long id;

        @Schema(description = "유저 닉네임")
        private String nickname;

        @Schema(description = "유저 이메일")
        private String email;

        @Schema(description = "유저 프로필 이미지")
        private String profileImageUrl;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "사용자 학습 정보 조회 응답")
    public static class UserLearningInfo {

        @Schema(description = "유저 닉네임")
        private String nickname;

        @Schema(description = "유저 프로필 이미지")
        private String profileImageUrl;

        @Schema(description = "유저 레벨")
        private Integer level;

        @Schema(description = "카테고리별 학습률")
        private List<CategoryLearningInfo> categoryLearningInfo;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카테고리별 학습률 조회 응답")
    public static class CategoryLearningInfo {

        @Schema(description = "카테고리 이름")
        private String name;

        @Schema(description = "해당 카테고리의 학습률")
        private int progress;

    }




}
