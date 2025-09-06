package com.example.ssg_tab.global.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionTest implements CommandLineRunner {

    private final StringRedisTemplate redisTemplate;

    public RedisConnectionTest(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            // Redis에 테스트 키-값 저장
            redisTemplate.opsForValue().set("redisTestKey", "connected");

            // 값 읽기
            String val = redisTemplate.opsForValue().get("redisTestKey");
            if ("connected".equals(val)) {
                System.out.println("Redis 연결 성공! 값: " + val);
            } else {
                System.out.println("Redis 연결 실패: 읽은 값이 예상과 다름");
            }
        } catch (Exception e) {
            System.err.println("Redis 연결 실패: " + e.getMessage());
        }
    }
}
