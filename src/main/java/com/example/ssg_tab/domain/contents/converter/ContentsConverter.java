package com.example.ssg_tab.domain.contents.converter;

import com.example.ssg_tab.domain.contents.dto.response.ContentsResponse;
import com.example.ssg_tab.domain.contents.entity.Contents;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContentsConverter {

    public static ContentsResponse.ContentsInfo toContentsInfo(Contents c) {
        List<ContentsResponse.CategoryInfo> categories =
                (c.getCategories() == null) ? Collections.emptyList()
                        : c.getCategories().stream()
                        .map(cat -> ContentsResponse.CategoryInfo.builder()
                                .categoryId(cat.getCategory().getId())   // 연관 엔티티 경로 주의
                                .name(cat.getCategory().getName())
                                .build())
                        .collect(Collectors.toList());

        return ContentsResponse.ContentsInfo.builder()
                .id(c.getId())
                .title(c.getTitle())
                .imageUrl(c.getImageUrl())
                .sourceUrl(c.getSourceUrl())
                .body(c.getBody())
                .categories(categories)
                .build();
    }

    public static ContentsResponse.ContentsPageResponse toContentsPageResponse(Page<Contents> page) {
        Page<ContentsResponse.ContentsInfo> dtoPage = page.map(ContentsConverter::toContentsInfo);
        return ContentsResponse.ContentsPageResponse.builder()
                .contentsPage(dtoPage)
                .build();
    }

    public static ContentsResponse.BookmarkResponse toBookmark(List<Contents> contentsList) {
        List<Contents> safe = (contentsList == null) ? Collections.emptyList() : contentsList;

        List<ContentsResponse.ContentsInfo> items = safe.stream()
                .map(ContentsConverter::toContentsInfo)
                .toList();

        return ContentsResponse.BookmarkResponse.builder()
                .contentsList(items)
                .build();
    }

}
