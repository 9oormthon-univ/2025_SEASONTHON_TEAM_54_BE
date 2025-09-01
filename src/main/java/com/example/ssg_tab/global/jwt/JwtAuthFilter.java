package com.example.ssg_tab.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 이미 인증된 상태면 패스
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = jwtTokenService.resolveToken(request);

            if (token != null) {
                try {
                    // 1. 파싱/검증
                    Jws<Claims> jws = jwtTokenService.parse(token);
                    Claims c = jws.getPayload();

                    // 2. access 토큰만 인증 세션으로 사용
                    if ("access".equals(c.getSubject())) {
                        Long uid = (c.get("uid") instanceof Number n) ? n.longValue()
                                : Long.valueOf(String.valueOf(c.get("uid")));

                        // 3. DB에서 UserDetails 로드(권한 포함)
                        UserDetails userDetails = customUserDetailService.loadUserByUsername(String.valueOf(uid));

                        // 4. 정식 Authentication 생성
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                } catch (Exception e) {

                }
            }
        }

        chain.doFilter(request, response);
    }

    // Swagger, auth 등은 필터 스킵
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs/");
    }
}
