package com.example.ssg_tab.domain.contents.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ContentsRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "컨텐츠 좋아요 요청")
    public static class Bookmark{

        @Schema(description = "좋아요를 누를 컨텐츠의 아이디")
        private Long contentsId;

    }
}
