package com.example.ssg_tab.global.util.source;

public record ContentsItem(
        String title,
        String imageUrl,   // 없으면 null
        String sourceUrl,   // 원본 URL
        String body
) {}
