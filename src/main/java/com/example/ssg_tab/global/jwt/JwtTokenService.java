package com.example.ssg_tab.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.access-token-exp-min}")
    private long accessExpMin;
    @Value("${jwt.refresh-token-exp-days}")
    private long refreshExpDays;

    private Key key;

    @PostConstruct
    void init() {
        // secret이 Base64인지/평문인지 자동 판단해서 키 생성
        Key k;
        try {
            byte[] decoded = Decoders.BASE64.decode(secret);
            k = Keys.hmacShaKeyFor(decoded);
        } catch (IllegalArgumentException notBase64) {
            // Base64 아니면 평문으로 간주
            k = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
        this.key = k;
    }

    public String createAccessToken(Long userId) {
        Instant now = Instant.now();
        Instant exp = now.plus(Duration.ofMinutes(accessExpMin));

        return Jwts.builder()
                .issuer(issuer)
                .subject("access")
                .claim("uid", userId)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long userId) {
        Instant now = Instant.now();
        Instant exp = now.plus(Duration.ofDays(refreshExpDays));

        return Jwts.builder()
                .issuer(issuer)
                .subject("refresh")
                .claim("uid", userId)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    // 토큰(액세스,리프레시 공통) 파싱, 서명/만료 검증
    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .clockSkewSeconds(0)
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token);
    }

    // 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 유효한 액세스 토큰인지
    public boolean isValidAccessToken(String token) {
        try {
            Jws<Claims> jws = parse(token);
            return !"refresh".equals(jws.getPayload().getSubject());
        } catch (Exception e) {
            return false;
        }
    }

    // 유효한 리프레시 토큰인지
    public boolean isValidRefreshToken(String token) {
        try {
            Jws<Claims> jws = parse(token);
            return "refresh".equals(jws.getPayload().getSubject());
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 유저 ID(uid) 추출
    public Long getUserId(String token) {
        Jws<Claims> jws = parse(token);
        Object uid = jws.getPayload().get("uid");
        if (uid instanceof Number n) return n.longValue();
        if (uid instanceof String s) return Long.parseLong(s);
        throw new IllegalStateException("uid claim not found");
    }
}
