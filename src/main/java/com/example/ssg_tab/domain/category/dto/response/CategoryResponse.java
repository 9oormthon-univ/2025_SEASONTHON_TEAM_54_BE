package com.example.ssg_tab.domain.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CategoryResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카테고리 리스트 응답")
    public static class CategoryListResponse {

        @Schema(description = "카테고리 리스트")
        private List<CategoryInfo> categoryList;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "각 카테고리의 정보")
    public static class CategoryInfo {

        @Schema(description = "카테고리의 아이디")
        private Long id;

        @Schema(description = "카테고리의 이름")
        private String name;

    }
}
