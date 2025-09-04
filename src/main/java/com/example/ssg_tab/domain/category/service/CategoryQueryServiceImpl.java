package com.example.ssg_tab.domain.category.service;

import com.example.ssg_tab.domain.category.converter.CategoryConverter;
import com.example.ssg_tab.domain.category.dto.response.CategoryResponse;
import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse.CategoryListResponse getCategoryList() {

        List<Category> categoryList = categoryRepository.findAll();

        return CategoryConverter.toCategoryListResponse(categoryList);

    }
}
