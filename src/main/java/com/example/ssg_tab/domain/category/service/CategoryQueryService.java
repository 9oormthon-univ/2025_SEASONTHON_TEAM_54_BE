package com.example.ssg_tab.domain.category.service;

import com.example.ssg_tab.domain.category.dto.response.CategoryResponse;

public interface CategoryQueryService {

    CategoryResponse.CategoryListResponse getCategoryList();
}
