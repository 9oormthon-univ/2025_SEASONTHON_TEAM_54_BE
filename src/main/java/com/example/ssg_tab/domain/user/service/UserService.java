package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.global.auth.info.KakaoUserInfo;

import java.util.Optional;

public interface UserService {

    User createUser(KakaoUserInfo userInfo);

    UserResponse.UserInfoResponse getUserInfo(Long userId);

}
