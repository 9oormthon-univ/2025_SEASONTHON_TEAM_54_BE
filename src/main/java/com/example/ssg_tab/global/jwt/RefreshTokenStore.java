package com.example.ssg_tab.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RefreshTokenStore {

    private final StringRedisTemplate redis;

    private String key(Long userId) { return "refresh:" + userId; }

    public void save(Long userId, String refreshToken, long days) {
        redis.opsForValue().set(key(userId), refreshToken, Duration.ofDays(days));
    }

    public String get(Long userId) {
        return redis.opsForValue().get(key(userId));
    }

    public void delete(Long userId) {
        redis.delete(key(userId));
    }
}
