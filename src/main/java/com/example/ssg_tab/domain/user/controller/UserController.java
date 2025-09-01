package com.example.ssg_tab.domain.user.controller;

import com.example.ssg_tab.domain.test.dto.response.TestResponse;
import com.example.ssg_tab.domain.user.dto.response.UserResponse;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.service.UserService;
import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "유저 정보 조회 API", description = "로그인한 사용자의 유저 정보를 조회합니다.")
    public ApiResponse<UserResponse.UserInfoResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetails){

        Long uid = Long.valueOf(userDetails.getUsername());   // 유저 ID
        UserResponse.UserInfoResponse response = userService.getUserInfo(uid);

        return ApiResponse.of(SuccessStatus._OK, response);
    }
}
