package com.example.ssg_tab.domain.user.entity.mapping;

import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.quiz.entity.Quiz;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_quiz")
public class UserQuiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

}
