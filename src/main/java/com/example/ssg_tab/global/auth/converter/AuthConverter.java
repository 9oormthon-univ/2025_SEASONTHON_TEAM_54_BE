package com.example.ssg_tab.global.auth.converter;

import com.example.ssg_tab.global.auth.dto.AuthResponse;

public class AuthConverter {

    public static AuthResponse.LoginResponse toLoginResponse(String accessToken, String refreshToken) {
        return AuthResponse.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
