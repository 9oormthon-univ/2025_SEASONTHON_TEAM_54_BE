package com.example.ssg_tab.domain.user.converter;

import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.entity.User;

public class UserConverter {

    public static UserResponse.UserInfoResponse toUserInfoResponse(User user) {

        return UserResponse.UserInfoResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .build();

    }
}
