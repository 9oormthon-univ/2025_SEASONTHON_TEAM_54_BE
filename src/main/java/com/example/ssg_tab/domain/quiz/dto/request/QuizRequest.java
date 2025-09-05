package com.example.ssg_tab.domain.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QuizRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "퀴즈 완료 요청")
    public static class QuizComplete {

        @Schema(description = "완료할 퀴즈의 아이디")
        private Long quizId;

    }

}
