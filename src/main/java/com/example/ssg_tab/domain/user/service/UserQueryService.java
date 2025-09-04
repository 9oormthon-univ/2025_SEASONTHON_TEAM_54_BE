package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.user.dto.response.UserResponse;

public interface UserQueryService {

    UserResponse.UserInfoResponse getUserInfo(Long userId);

}
