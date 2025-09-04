package com.example.ssg_tab.domain.category.converter;

import com.example.ssg_tab.domain.category.dto.response.CategoryResponse;
import com.example.ssg_tab.domain.category.entity.Category;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CategoryConverter {

    public static CategoryResponse.CategoryListResponse toCategoryListResponse(List<Category> categoryList) {

        List<CategoryResponse.CategoryInfo> items = Optional.ofNullable(categoryList)
                .orElseGet(List::of)
                .stream()
                .filter(Objects::nonNull)
                .map(CategoryConverter::toCategoryInfo)
                .toList();

        return CategoryResponse.CategoryListResponse.builder()
                .categoryList(items)
                .build();

    }

    public static CategoryResponse.CategoryInfo toCategoryInfo(Category category) {
        return CategoryResponse.CategoryInfo.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
