package com.example.ssg_tab.global.auth.service;

import com.example.ssg_tab.domain.user.service.UserCreateService;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.exception.GeneralException;
import com.example.ssg_tab.global.jwt.JwtTokenService;
import com.example.ssg_tab.global.auth.converter.AuthConverter;
import com.example.ssg_tab.global.auth.dto.AuthRequest;
import com.example.ssg_tab.global.auth.dto.AuthResponse;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import com.example.ssg_tab.global.jwt.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenService jwtTokenService;
    private final RefreshTokenStore refreshStore;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserCreateService userCreateService;

    @Value("${jwt.refresh-token-exp-days}") private long refreshExpDays;

    @Transactional
    public AuthResponse.LoginResponse kakaoLogin(AuthRequest.KakaoLoginRequest request) {

        // 1. 회원 조회 후 없으면 생성
        User user = userCreateService.createKakaoUser(request);

        // 2. Jwt 발급
        String accessToken = jwtTokenService.createAccessToken(user.getId());
        String refreshToken = jwtTokenService.createRefreshToken(user.getId());
        refreshStore.save(user.getId(), refreshToken, refreshExpDays);

        return AuthConverter.toLoginResponse(user, accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse.SignUpResponse signUp(AuthRequest.EmailSignUpRequest request) {

        // 1. 유저 생성
        User user = userCreateService.createUser(request);

        // 2. 토큰 발급
        String accessToken = jwtTokenService.createAccessToken(user.getId());
        String refreshToken = jwtTokenService.createRefreshToken(user.getId());
        refreshStore.save(user.getId(), refreshToken, refreshExpDays);

        return AuthConverter.toSignUpResponse(user, accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse.LoginResponse emailLogin(AuthRequest.EmailLoginRequest request) {

        String email = request.getEmail();

        // 1. 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        // 2. 비밀번호 검사
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.INVALID_CREDENTIALS);
        }

        // 3. 토큰 발급
        String accessToken = jwtTokenService.createAccessToken(user.getId());
        String refreshToken = jwtTokenService.createRefreshToken(user.getId());
        refreshStore.save(user.getId(), refreshToken, refreshExpDays);

        return AuthConverter.toLoginResponse(user, accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse.ReissueResponse reissue(AuthRequest.RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        var claims = jwtTokenService.parse(refreshToken).getPayload();
        Long uid = claims.get("uid", Long.class);

        // Redis에 저장된 최신 토큰과 비교(회전 방지)
        String stored = refreshStore.get(uid);
        if (stored == null || !stored.equals(refreshToken)) {
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }

        String newAccess = jwtTokenService.createAccessToken(uid);
        String newRefresh = jwtTokenService.createRefreshToken(uid);
        refreshStore.save(uid, newRefresh, refreshExpDays);

        return AuthConverter.toReissueResponse(newAccess, newRefresh);
    }
}
