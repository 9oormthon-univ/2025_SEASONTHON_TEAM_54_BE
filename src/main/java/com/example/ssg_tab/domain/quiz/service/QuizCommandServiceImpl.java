package com.example.ssg_tab.domain.quiz.service;

import com.example.ssg_tab.domain.quiz.dto.request.QuizRequest;
import com.example.ssg_tab.domain.quiz.entity.Quiz;
import com.example.ssg_tab.domain.quiz.repository.QuizRepository;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.entity.mapping.UserCategory;
import com.example.ssg_tab.domain.user.entity.mapping.UserQuiz;
import com.example.ssg_tab.domain.user.repository.UserCategoryRepository;
import com.example.ssg_tab.domain.user.repository.UserQuizRepository;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizCommandServiceImpl implements QuizCommandService {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final UserQuizRepository userQuizRepository;
    private final UserCategoryRepository userCategoryRepository;

    @Override
    @Transactional
    public void completeQuiz(Long userId, QuizRequest.QuizComplete request) {

        Long quizId = request.getQuizId();

        // 1. 유저 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        // 2. 퀴즈 조회
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new GeneralException(ErrorStatus.QUIZ_NOT_FOUND));

        // 3. 이미 완료된 퀴즈인지 검증
        if (userQuizRepository.existsByUserIdAndQuizId(userId, quizId)) {
            throw new GeneralException(ErrorStatus.QUIZ_ALREADY_COMPLETE);
        }

        // 4. 유저-퀴즈 엔티티 생성
        UserQuiz userQuiz = UserQuiz.builder()
                .user(user)
                .quiz(quiz)
                .build();
        userQuizRepository.save(userQuiz);

        // 5. 유저의 퀴즈 카테고리의 학습률 증가
        UserCategory userCategory = userCategoryRepository.findByUserIdAndCategoryId(userId, quiz.getCategory().getId());
        userCategory.updateProgress(userCategory.getProgress() + 25);
        userCategoryRepository.save(userCategory);

    }
}
