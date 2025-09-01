package com.example.ssg_tab.global.auth.info;

public record KakaoTokenInfo(
        Long id,          // 회원번호
        Integer expires_in,
        Integer app_id
) {}