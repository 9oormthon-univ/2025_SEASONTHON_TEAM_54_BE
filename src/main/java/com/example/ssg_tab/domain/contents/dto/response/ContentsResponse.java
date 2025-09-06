package com.example.ssg_tab.domain.contents.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

public class ContentsResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "컨텐츠 조회 API에 대한 응답")
    public static class ContentsPageResponse {

        private Page<ContentsInfo> contentsPage;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "컨텐츠 정보")
    public static class ContentsInfo {

        @Schema(description = "컨텐츠의 아이디")
        private Long id;

        @Schema(description = "컨텐츠의 제목")
        private String title;

        @Schema(description = "컨텐츠의 이미지 URL")
        private String imageUrl;

        @Schema(description = "컨텐츠의 본문 URL")
        private String sourceUrl;

        @Schema(description = "컨텐츠의 내용")
        private String body;

        @Schema(description = "컨텐츠의 카테고리들")
        private List<CategoryInfo> categories;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카테고리 정보")
    public static class CategoryInfo {

        @Schema(description = "카테고리의 아이디")
        private Long categoryId;

        @Schema(description = "카테고리의 이름")
        private String name;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "좋아요한 컨텐츠 리스트")
    public static class Bookmark {

        @Schema(description = "좋아요 컨텐츠 리스트")
        private List<ContentsInfo> contentsList;
    }

}
