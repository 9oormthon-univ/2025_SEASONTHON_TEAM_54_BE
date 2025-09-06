package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.user.converter.UserConverter;
import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.entity.mapping.UserCategory;
import com.example.ssg_tab.domain.user.repository.UserCategoryRepository;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;


    @Override
    @Transactional(readOnly = true)
    public UserResponse.UserInfo getUserInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        return UserConverter.toUserInfoResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse.UserLearningInfo getUserLearningInfo(Long userId) {

        // 1. 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        // 2. 사용자와 카테고리
        List<UserCategory> userCategories = userCategoryRepository.findByUser(user);

        return UserConverter.toUserLearningInfo(user, userCategories);
    }

}
