package com.example.ssg_tab.domain.quiz.repository;

import com.example.ssg_tab.domain.quiz.entity.Quiz;
import com.example.ssg_tab.domain.quiz.entity.enums.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("""
           select q 
           from Quiz q 
             join fetch q.category c
           where (:categoryId is null or c.id = :categoryId)
             and (:difficulty is null or q.difficulty = :difficulty)
           order by q.id desc
           """)
    List<Quiz> findAllByCategoryIdAndDifficulty(
            @Param("categoryId") Long categoryId,
            @Param("difficulty") Difficulty difficulty
    );

}
