package com.example.ssg_tab.domain.user.service;

import com.example.ssg_tab.domain.user.dto.request.UserRequest;
import com.example.ssg_tab.domain.user.entity.User;

import java.util.List;

public interface OnboardingService {

    void onboarding(Long userId, UserRequest.OnboardingRequest request);

    void attachCategories(User user, List<Long> categories);
}
