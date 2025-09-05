package com.example.ssg_tab.domain.user.repository;

import com.example.ssg_tab.domain.user.entity.mapping.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {
    boolean existsByUserIdAndQuizId(Long userId, Long quizId);
}
