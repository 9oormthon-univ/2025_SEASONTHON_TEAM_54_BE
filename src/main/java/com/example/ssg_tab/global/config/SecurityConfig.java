package com.example.ssg_tab.global.config;

import com.example.ssg_tab.global.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Swagger & OpenAPI
                        .requestMatchers(
                                "/swagger-ui.html",     // 커스텀한 UI 진입점
                                "/swagger-ui/**",       // UI 리소스
                                "/api-docs/**",         // 커스텀한 api-docs 경로
                                "/v3/api-docs/**",      // 혹시 모를 기본 경로도 함께 허용
                                "/auth/**",             // 인증 경로
                                "/dev/**",               // 개발용 API
                                "/**"
                        ).permitAll()

                        // 테스트용
                        .requestMatchers("/test/**").permitAll()

                        // 그 외는 인증 필요
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

