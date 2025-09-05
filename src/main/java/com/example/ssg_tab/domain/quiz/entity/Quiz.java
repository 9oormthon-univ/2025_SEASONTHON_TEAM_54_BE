package com.example.ssg_tab.domain.quiz.entity;

import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.quiz.entity.enums.Difficulty;
import com.example.ssg_tab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "quiz")
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "options" ,columnDefinition = "json", nullable = false)
    @Builder.Default
    private List<String> options = new ArrayList<>(); // 문항 리스트

    // 정답 인덱스 : options에서 정답 문항의 인덱스, 0부터
    @Column(name = "correct_index", nullable = false)
    private Integer correctIndex;

    // 관련 카테고리 하나
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // 난이도
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty;


}
