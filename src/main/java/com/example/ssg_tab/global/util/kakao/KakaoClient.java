package com.example.ssg_tab.global.util.kakao;

import com.example.ssg_tab.global.auth.info.KakaoTokenInfo;
import com.example.ssg_tab.global.auth.info.KakaoUserInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class KakaoClient {

    private final RestClient rest = RestClient.create();

    public KakaoTokenInfo getTokenInfo(String accessToken) {
        ResponseEntity<KakaoTokenInfo> res = rest.get()
                .uri("https://kapi.kakao.com/v1/user/access_token_info")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .toEntity(KakaoTokenInfo.class);
        return res.getBody();
    }

    public KakaoUserInfo getUserInfo(String accessToken) {
        ResponseEntity<KakaoUserInfo> res = rest.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .toEntity(KakaoUserInfo.class);
        return res.getBody();
    }
}


