package com.example.ssg_tab.domain.quiz.controller;

import com.example.ssg_tab.domain.quiz.dto.request.QuizRequest;
import com.example.ssg_tab.domain.quiz.dto.response.QuizResponse;
import com.example.ssg_tab.domain.quiz.entity.enums.Difficulty;
import com.example.ssg_tab.domain.quiz.repository.QuizRepository;
import com.example.ssg_tab.domain.quiz.service.QuizCommandService;
import com.example.ssg_tab.domain.quiz.service.QuizQueryService;
import com.example.ssg_tab.domain.test.dto.response.TestResponse;
import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
@Tag(name = "Quiz", description = "퀴즈 관련 API")
public class QuizController {

    private final QuizQueryService quizQueryService;
    private final QuizCommandService quizCommandService;

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "퀴즈 조회 API", description = "카테고리 아이디와 난이도를 입력하여 조건에 맞는 문제들을 조회합니다.")
    public ApiResponse<QuizResponse.QuizList> getQuiz(@RequestParam("categoryId") Long categoryId,
                                                             @RequestParam("difficulty") Difficulty difficulty){

        QuizResponse.QuizList response = quizQueryService.getQuiz(categoryId, difficulty);

        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "퀴즈 완료 API", description = "퀴즈 아이디를 입력하여 사용자가 해당 퀴즈를 완료하고 학습률을 업데이트 합니다.")
    public ApiResponse<Object> createQuiz(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestBody @Valid QuizRequest.QuizComplete request){

        Long uid = Long.valueOf(userDetails.getUsername());
        quizCommandService.completeQuiz(uid, request);

        return ApiResponse.of(SuccessStatus.QUIZ_COMPLETE, null);
    }
}
