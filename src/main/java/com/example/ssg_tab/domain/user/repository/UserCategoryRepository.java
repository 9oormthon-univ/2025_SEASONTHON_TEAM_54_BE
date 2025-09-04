package com.example.ssg_tab.domain.user.repository;

import com.example.ssg_tab.domain.user.entity.mapping.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    boolean existsByUserIdAndCategoryId(Long userId, Long categoryId);
}
