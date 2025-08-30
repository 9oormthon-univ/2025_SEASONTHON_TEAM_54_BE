package com.example.ssg_tab.domain.test.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TestResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "테스트 응답 DTO에 대한 설명")
    public static class TestResponseDTO {

        @Schema(description = "테스트 응답 DTO의 각 필드에 대한 설명")
        private Long number;

    }
}
