package com.example.ssg_tab.domain.category.repository;

import com.example.ssg_tab.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
