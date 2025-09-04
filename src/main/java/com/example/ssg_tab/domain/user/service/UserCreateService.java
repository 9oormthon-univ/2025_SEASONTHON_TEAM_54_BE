package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.global.auth.dto.AuthRequest;

import java.util.List;

public interface UserCreateService {

    User createKakaoUser(AuthRequest.KakaoLoginRequest userInfo);

    User createUser(AuthRequest.EmailSignUpRequest userInfo);

}
