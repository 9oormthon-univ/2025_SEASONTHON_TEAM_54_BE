package com.example.ssg_tab.global.util.source;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
public class NaverNewsClient implements SourceClient {

    private final WebClient web = WebClient.builder()
            .baseUrl("https://openapi.naver.com")
            .build();

    @Value("${naver.client-id}")
    private String clientId;
    @Value("${naver.client-secret}")
    private String clientSecret;

    @Override
    public List<ContentsItem> fetchTopN(String query, Duration timeout) {
        List<ContentsItem> out = new ArrayList<>();
        Set<String> seenUrl = new HashSet<>();

        Map body = web.get()
                .uri(uri -> uri.path("/v1/search/news.json")
                        .queryParam("query", query)   // 카테고리의 이름으로 검색
                        .queryParam("display", 10)
                        .queryParam("start", 1)       // 1 이상
                        .queryParam("sort", "sim")    // 유사도순
                        .build())
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(timeout)
                .onErrorReturn(Collections.emptyMap())
                .block();

        List<Map<String,Object>> items =
                (List<Map<String,Object>>) (body != null ? body.getOrDefault("items", List.of()) : List.of());

        for (var it : items) {
            String title = text((String) it.get("title"));            // 태그 제거된 제목
            String desc  = text((String) it.get("description"));      // 태그 제거된 요약
            String origin= (String) it.get("originallink");
            String link  = (String) it.get("link");
            String url   = (origin != null && !origin.isBlank()) ? origin : link;

            if (title == null || url == null) continue;

            // 쿼리 키워드가 제목/요약에 모두 포함되는 결과만 통과
            if (!matchesQuery(title, desc, query)) continue;

            // body는 150자(코드포인트 기준)로 안전하게 자르기
            String clippedBody = clip150(desc);

            if (seenUrl.add(url)) {
                out.add(new ContentsItem(title, null, url, clippedBody));
                if (out.size() >= 10) break;
            }
        }
        return out;
    }

    /** HTML → 텍스트로 */
    private static String text(String s) {
        return s == null ? null : org.jsoup.Jsoup.parse(s).text();
    }

    /** 쿼리의 모든 토큰이 (제목+요약) 안에 포함되어야 true */
    private static boolean matchesQuery(String title, String desc, String query) {
        String hay = ((title == null ? "" : title) + " " + (desc == null ? "" : desc)).toLowerCase();
        for (String kw : tokenize(query)) {
            if (!hay.contains(kw.toLowerCase())) return false;
        }
        return true;
    }

    /** 공백 기준 토큰화(따옴표/괄호 제거), 길이 1 이하는 무시 */
    private static List<String> tokenize(String q) {
        if (q == null) return List.of();
        String cleaned = q.replaceAll("[\"'()\\[\\]{}]", " ").trim();
        String[] parts = cleaned.split("\\s+");
        List<String> out = new ArrayList<>();
        for (String p : parts) {
            if (p.length() > 1) out.add(p);
        }
        return out.isEmpty() ? List.of(cleaned) : out;
    }

    /** 코드포인트 기준 150자로 자르고 줄임표 붙임 */
    private static String clip150(String s) {
        if (s == null) return null;
        String normalized = s.replaceAll("\\s+", " ").trim();
        if (normalized.isEmpty()) return null;

        final int max = 150;
        int cps = normalized.codePointCount(0, normalized.length());
        if (cps <= max) return normalized;

        int end = normalized.offsetByCodePoints(0, max);
        return normalized.substring(0, end) + "…";
    }
}