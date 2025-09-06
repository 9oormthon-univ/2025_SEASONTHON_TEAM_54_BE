package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.category.repository.CategoryRepository;
import com.example.ssg_tab.domain.user.dto.request.UserRequest;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.entity.enums.UserStep;
import com.example.ssg_tab.domain.user.entity.mapping.UserCategory;
import com.example.ssg_tab.domain.user.repository.UserCategoryRepository;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;

    @Override
    @Transactional
    public void onboarding(Long userId, UserRequest.OnboardingRequest request) {

        // 1. 사용자 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        // 2. 사용자 단계 검사
//        if(user.getStep() != UserStep.ONBOARDING) throw new GeneralException(ErrorStatus.ONBOARDING_STEP_ERROR);

        // 3. 온보딩 내용 업데이트
        if(user.getSocialId() == null){ // 카카오 유저가 아닐 경우
            user.updateNickname(request.getNickname());
            user.updateProfileImageUrl(request.getProfileImageUrl());
        }
        user.updateRegion(request.getRegion());
        user.updateAgeBand(request.getAgeBand());
        user.updateJob(request.getJob());
        attachCategories(user, request.getCategoryIds());

        // 온보딩 성공 시 단계를 MAIN으로 설정
        user.updateStep(UserStep.MAIN);

        userRepository.save(user);
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
