package com.example.ssg_tab.domain.user.converter;

import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.entity.mapping.UserCategory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserResponse.UserInfo toUserInfoResponse(User user) {

        return UserResponse.UserInfo.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .build();

    }

    public static UserResponse.UserLearningInfo toUserLearningInfo(User user, List<UserCategory> userCategories) {

        List<UserResponse.CategoryLearningInfo> categoryLearningInfos =
                (userCategories == null || userCategories.isEmpty())
                        ? Collections.emptyList()
                        : userCategories.stream()
                        .map(uc -> UserResponse.CategoryLearningInfo.builder()
                                .name(uc.getCategory().getName())
                                .progress(uc.getProgress())
                                .build()
                        )
                        .collect(Collectors.toList());

        return UserResponse.UserLearningInfo.builder()
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .level(user.getLevel())
                .categoryLearningInfo(categoryLearningInfos)
                .build();
    }

}
