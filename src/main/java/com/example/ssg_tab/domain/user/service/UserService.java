package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.global.auth.dto.AuthRequest;
import com.example.ssg_tab.global.auth.info.KakaoUserInfo;

import java.util.List;

public interface UserService {

    User createKakaoUser(AuthRequest.KakaoLoginRequest userInfo);

    User createUser(AuthRequest.EmailSignUpRequest userInfo);

    UserResponse.UserInfoResponse getUserInfo(Long userId);

    void attachCategories(User user, List<Long> categories);
}
