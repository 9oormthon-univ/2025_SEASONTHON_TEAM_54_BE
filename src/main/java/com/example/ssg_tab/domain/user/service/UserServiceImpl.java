package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.category.repository.CategoryRepository;
import com.example.ssg_tab.domain.user.converter.UserConverter;
import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.entity.enums.UserRole;
import com.example.ssg_tab.domain.user.entity.mapping.UserCategory;
import com.example.ssg_tab.domain.user.repository.UserCategoryRepository;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.auth.dto.AuthRequest;
import com.example.ssg_tab.global.auth.info.KakaoUserInfo;
import com.example.ssg_tab.global.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createKakaoUser(KakaoUserInfo userInfo) {

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
    @Transactional
    public User createUser(AuthRequest.EmailSignUpRequest userInfo) {

        final String email = userInfo.getEmail().trim().toLowerCase();

        // 1. 이메일 중복 검사
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new GeneralException(ErrorStatus.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(userInfo.getEmail())
                .password(null) // 아래 encode해서 세팅
                .nickname(userInfo.getNickname())
                .socialId(null) // 이메일 회원가입 경로
                .profileImageUrl(null)
                .ageBand(userInfo.getAgeBand())
                .region(userInfo.getRegion())
                .job(userInfo.getJob())
                .role(UserRole.USER)
                .build();

        user.encodePassword(passwordEncoder.encode(userInfo.getPassword()));
        return userRepository.save(user);
    }

    // 관심 카테고리 매핑 (3~5개)
    @Transactional
    public void attachCategories(User user, List<Long> categoryIds) {

        // 1. 카테고리 개수 검증
        var ids = new LinkedHashSet<>(Optional.ofNullable(categoryIds).orElse(List.of()));
        if (ids.size() < 3 || ids.size() > 5) {
            throw new GeneralException(ErrorStatus.INVALID_INTEREST_COUNT); // "관심 카테고리는 3~5개를 선택해야 합니다."
        }

        // 2. 존재하는 카테고리 로드
        var categories = categoryRepository.findAllById(ids);

        // 3. 연관관계 생성
        for (Category c : categories) {
            if (!userCategoryRepository.existsByUserIdAndCategoryId(user.getId(), c.getId())) {
                userCategoryRepository.save(
                        UserCategory.builder()
                                .user(user)
                                .category(c)
                                .build()
                );
            }
        }
    }

    @Override
    public UserResponse.UserInfoResponse getUserInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        return UserConverter.toUserInfoResponse(user);
    }
}
