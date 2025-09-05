package com.example.ssg_tab.domain.quiz.service;

import com.example.ssg_tab.domain.quiz.dto.request.QuizRequest;

public interface QuizCommandService {

    void completeQuiz(Long userId, QuizRequest.QuizComplete request);
}
