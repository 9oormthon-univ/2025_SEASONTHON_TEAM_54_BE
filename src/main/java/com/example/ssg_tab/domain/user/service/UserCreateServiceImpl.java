package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.category.repository.CategoryRepository;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.entity.enums.UserRole;
import com.example.ssg_tab.domain.user.entity.mapping.UserCategory;
import com.example.ssg_tab.domain.user.repository.UserCategoryRepository;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.util.KakaoClient;
import com.example.ssg_tab.global.auth.dto.AuthRequest;
import com.example.ssg_tab.global.auth.info.KakaoTokenInfo;
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
public class UserCreateServiceImpl implements UserCreateService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoClient kakaoClient;

    @Override
    @Transactional
    public User createKakaoUser(AuthRequest.KakaoLoginRequest userInfo) {

        // 1. 카카오 토큰 유효성 확인
        KakaoTokenInfo tokenInfo = kakaoClient.getTokenInfo(userInfo.getKakaoAccessToken());
        Long kakaoId = tokenInfo.id(); // 소셜 식별자

        // 2. 카카오에서 제공하는 사용자 정보 조회(닉네임,이메일 등)
        KakaoUserInfo kakaoUserInfo = kakaoClient.getUserInfo(userInfo.getKakaoAccessToken());

        // 3. 회원 조회 후 없으면 생성
        User user = userRepository.findBySocialId(kakaoId)
                .orElseGet(() -> User.builder()
                        .email(kakaoUserInfo.getEmail())
                        .password(null)
                        .nickname(kakaoUserInfo.getNickname())
                        .socialId(kakaoUserInfo.id())
                        .profileImageUrl(kakaoUserInfo.getProfileImageUrl())
                        .ageBand(userInfo.getAgeBand())
                        .region(userInfo.getRegion())
                        .job(userInfo.getJob())
                        .role(UserRole.USER)
                        .build());

        return userRepository.save(user);

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
                .password(null) // 아래 encode 해서 세팅
                .nickname(userInfo.getNickname())
                .socialId(null) // 이메일 회원가입 경로
                .profileImageUrl(null)  // 프로필 이미지 등록 방법 필요
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
}
