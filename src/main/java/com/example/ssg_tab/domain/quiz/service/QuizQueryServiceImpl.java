package com.example.ssg_tab.domain.quiz.service;

import com.example.ssg_tab.domain.quiz.converter.QuizConverter;
import com.example.ssg_tab.domain.quiz.dto.response.QuizResponse;
import com.example.ssg_tab.domain.quiz.entity.Quiz;
import com.example.ssg_tab.domain.quiz.entity.enums.Difficulty;
import com.example.ssg_tab.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizQueryServiceImpl implements QuizQueryService {

    private final QuizRepository quizRepository;

    @Override
    @Transactional(readOnly = true)
    public QuizResponse.QuizList getQuiz(Long categoryId, Difficulty difficulty) {

        List<Quiz> quizList = quizRepository.findAllByCategoryIdAndDifficulty(categoryId, difficulty);

        return QuizConverter.toQuizList(quizList);
    }
}
