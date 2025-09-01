package com.example.ssg_tab.global.auth.service;

import com.example.ssg_tab.domain.user.service.UserService;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.auth.KakaoClient;
import com.example.ssg_tab.global.exception.GeneralException;
import com.example.ssg_tab.global.jwt.JwtTokenService;
import com.example.ssg_tab.global.auth.info.KakaoUserInfo;
import com.example.ssg_tab.global.auth.converter.AuthConverter;
import com.example.ssg_tab.global.auth.dto.AuthRequest;
import com.example.ssg_tab.global.auth.dto.AuthResponse;
import com.example.ssg_tab.global.auth.info.KakaoTokenInfo;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import com.example.ssg_tab.global.jwt.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenService jwtTokenService;
    private final RefreshTokenStore refreshStore;

    private final KakaoClient kakaoClient;

    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${jwt.refresh-token-exp-days}") private long refreshExpDays;

    @Transactional
    public AuthResponse.LoginResponse kakaoLogin(AuthRequest.KakaoLoginRequest request) {
        // 1. 카카오 토큰 유효성 확인
        KakaoTokenInfo tokenInfo = kakaoClient.getTokenInfo(request.getKakaoAccessToken());
        Long kakaoId = tokenInfo.id(); // 소셜 식별자

        // 2. 사용자 정보 조회(닉네임/이메일)
        KakaoUserInfo userInfo = kakaoClient.getUserInfo(request.getKakaoAccessToken());
        String nickname = userInfo.getNickname();
        String email = userInfo.getEmail(); // 동의 안했으면 null
        String profileImageUrl = userInfo.getProfileImageUrl();

        // 3. 회원 조회 후 없으면 생성
        User user = userRepository.findBySocialId(kakaoId)
                .orElseGet(() -> userService.createUser(userInfo));

        // 4. Jwt 발급
        String accessToken = jwtTokenService.createAccessToken(user.getId());
        String refreshToken = jwtTokenService.createRefreshToken(user.getId());
        refreshStore.save(user.getId(), refreshToken, refreshExpDays);

        return AuthConverter.toLoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse.LoginResponse reissue(AuthRequest.RefreshTokenRequest request) {

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

        return AuthConverter.toLoginResponse(newAccess, newRefresh);
    }
}
