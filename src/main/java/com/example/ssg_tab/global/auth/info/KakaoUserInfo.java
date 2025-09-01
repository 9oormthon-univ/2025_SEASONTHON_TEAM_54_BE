package com.example.ssg_tab.global.auth.info;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfo(
        Long id,
        Map<String,Object> kakao_account,
        Map<String,Object> properties
) {
    @SuppressWarnings("unchecked")
    private Map<String,Object> getProfile() {
        if (kakao_account == null) return null;
        Object p = kakao_account.get("profile");
        return (p instanceof Map<?,?> m) ? (Map<String,Object>) m : null;
    }

    private static String asNonBlankString(Object value) {
        return (value instanceof String str && !str.isBlank()) ? str : null;
    }

    public String getNickname() {
        String n1 = asNonBlankString(getProfile() != null ? getProfile().get("nickname") : null);
        if (n1 != null) return n1;
        return asNonBlankString(properties != null ? properties.get("nickname") : null);
    }

    public String getEmail() {
        return asNonBlankString(kakao_account != null ? kakao_account.get("email") : null);
    }

    public String getProfileImageUrl() {
        String url = asNonBlankString(getProfile() != null ? getProfile().get("profile_image_url") : null); // 권장
        if (url != null) return url;
        return asNonBlankString(properties != null ? properties.get("profile_image") : null);         // 폴백(레거시)
    }
}
