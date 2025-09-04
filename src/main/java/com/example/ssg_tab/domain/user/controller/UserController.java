package com.example.ssg_tab.domain.user.controller;

import com.example.ssg_tab.domain.user.dto.request.UserRequest;
import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.service.OnboardingService;
import com.example.ssg_tab.domain.user.service.UserCreateService;
import com.example.ssg_tab.domain.user.service.UserQueryService;
import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserQueryService userQueryService;
    private final OnboardingService onboardingService;

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "유저 정보 조회 API", description = "로그인한 사용자의 유저 정보를 조회합니다.")
    public ApiResponse<UserResponse.UserInfoResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetails){

        Long uid = Long.valueOf(userDetails.getUsername());   // 유저 ID
        UserResponse.UserInfoResponse response = userQueryService.getUserInfo(uid);

        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @PostMapping(value = "/onboarding", produces = "application/json")
    @Operation(summary = "사용자 온보딩 API", description = "온보딩에서 입력받은 사용자의 정보를 반영합니다.")
    public ApiResponse<Object> onboarding(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestBody @Valid UserRequest.OnboardingRequest request){

        Long uid = Long.valueOf(userDetails.getUsername());
        onboardingService.onboarding(uid, request);

        return ApiResponse.of(SuccessStatus.ONBOARDING_SUCCESS, null);
    }
}
