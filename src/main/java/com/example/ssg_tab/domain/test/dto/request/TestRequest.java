package com.example.ssg_tab.domain.test.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TestRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "테스트 요청 DTO에 대한 설명")
    public static class TestRequestDTO {

        @Schema(description = "테스트 요청 DTO의 각 필드에 대한 설명")
        private Long number;

    }

}
