package com.example.ssg_tab.global.auth.controller;

import com.example.ssg_tab.global.auth.dto.AuthRequest;
import com.example.ssg_tab.global.auth.dto.AuthResponse;
import com.example.ssg_tab.global.auth.service.AuthService;
import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "소셜 로그인, 인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login/kakao", produces = "application/json")
    @Operation(summary = "카카오 로그인 API", description = "카카오를 통해 소셜 로그인/회원가입 후 토큰 발급")
    public ApiResponse<AuthResponse.LoginResponse> kakaoLogin(@RequestBody @Valid AuthRequest.KakaoLoginRequest request){

        AuthResponse.LoginResponse response = authService.kakaoLogin(request);

        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @PostMapping(value = "/signup/email", produces = "application/json")
    @Operation(summary = "이메일 회원가입 API", description = "이메일과 비밀번호 입력 후 회원가입")
    public ApiResponse<AuthResponse.SignUpResponse> signup(@RequestBody @Valid AuthRequest.EmailSignUpRequest request){

        AuthResponse.SignUpResponse response = authService.signUp(request);

        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @PostMapping(value ="/login/email", produces = "application/json")
    @Operation(summary = "이메일 로그인 API", description = "이메일 회원가입 시 입력한 이메일과 비밀번호를 통해 로그인 후 토큰 발급")
    public ApiResponse<AuthResponse.LoginResponse> emailLogin(@RequestBody @Valid AuthRequest.EmailLoginRequest request){

        AuthResponse.LoginResponse response = authService.emailLogin(request);

        return ApiResponse.of(SuccessStatus._OK, response);

    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급 API", description = "리프레쉬 토큰을 이용해 토큰을 재발급")
    public ApiResponse<AuthResponse.ReissueResponse> reissue(@RequestBody @Valid AuthRequest.RefreshTokenRequest request) {

        AuthResponse.ReissueResponse response = authService.reissue(request);

        return ApiResponse.of(SuccessStatus._OK, response);
    }
}
