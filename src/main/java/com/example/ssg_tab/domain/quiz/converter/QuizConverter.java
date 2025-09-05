package com.example.ssg_tab.domain.quiz.converter;

import com.example.ssg_tab.domain.quiz.dto.response.QuizResponse;
import com.example.ssg_tab.domain.quiz.entity.Quiz;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuizConverter {

    public static QuizResponse.QuizInfo toQuizInfo(Quiz q) {
        if (q == null) return null;

        List<String> options = q.getOptions() != null ? q.getOptions() : Collections.emptyList();

        return QuizResponse.QuizInfo.builder()
                .id(q.getId())
                .question(q.getQuestion())
                .options(options)
                .correctIndex(q.getCorrectIndex())
                .build();
    }

    public static QuizResponse.QuizList toQuizList(List<Quiz> quizzes) {
        List<QuizResponse.QuizInfo> quizInfos =
                (quizzes == null ? Collections.<Quiz>emptyList() : quizzes)
                        .stream()
                        .filter(Objects::nonNull)
                        .map(QuizConverter::toQuizInfo)
                        .collect(Collectors.toList());

        return QuizResponse.QuizList.builder()
                .quizList(quizInfos)
                .build();
    }
}
