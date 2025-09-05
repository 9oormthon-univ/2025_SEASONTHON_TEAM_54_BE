package com.example.ssg_tab.domain.quiz.service;

import com.example.ssg_tab.domain.quiz.dto.response.QuizResponse;
import com.example.ssg_tab.domain.quiz.entity.enums.Difficulty;

public interface QuizQueryService {
    QuizResponse.QuizList getQuiz(Long categoryId, Difficulty difficulty);
}
