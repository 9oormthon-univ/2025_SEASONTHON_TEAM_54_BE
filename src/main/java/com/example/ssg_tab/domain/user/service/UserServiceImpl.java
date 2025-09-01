package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.user.converter.UserConverter;
import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.entity.enums.UserRole;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.auth.info.KakaoUserInfo;
import com.example.ssg_tab.global.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(KakaoUserInfo userInfo) {

        User created = User.builder()
                .socialId(userInfo.id())
                .nickname(userInfo.getNickname())
                .email(userInfo.getEmail())
                .role(UserRole.USER)
                .profileImageUrl(userInfo.getProfileImageUrl())
                .build();
        return userRepository.save(created);

    }

    @Override
    public UserResponse.UserInfoResponse getUserInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        return UserConverter.toUserInfoResponse(user);
    }
}
