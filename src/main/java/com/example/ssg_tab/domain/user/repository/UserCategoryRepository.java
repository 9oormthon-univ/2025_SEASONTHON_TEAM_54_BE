package com.example.ssg_tab.domain.user.repository;

import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.entity.mapping.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    boolean existsByUserIdAndCategoryId(Long userId, Long categoryId);
    List<UserCategory> findByUser(User user);
    UserCategory findByUserIdAndCategoryId(Long userId, Long categoryId);
}
