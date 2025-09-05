package com.example.ssg_tab.domain.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class QuizResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "퀴즈 리스트")
    public static class QuizList {

        @Schema(description = "퀴즈 리스트")
        private List<QuizInfo> quizList;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "퀴즈 하나의 정보")
    public static class QuizInfo {

        @Schema(description = "퀴즈 ID")
        private Long id;

        @Schema(description = "퀴즈 내용")
        private String question;

        @Schema(description = "퀴즈의 문항 리스트")
        private List<String> options;

        @Schema(description = "퀴즈 정답의 인덱스, options의 인덱스(0부터 시작)")
        private Integer correctIndex;

    }
}
