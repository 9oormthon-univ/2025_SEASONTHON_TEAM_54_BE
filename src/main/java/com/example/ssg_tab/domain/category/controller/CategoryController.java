package com.example.ssg_tab.domain.category.controller;

import com.example.ssg_tab.domain.category.dto.response.CategoryResponse;
import com.example.ssg_tab.domain.category.service.CategoryQueryService;
import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Tag(name = "Category", description = "카테고리 관련 API")
public class CategoryController {

    private final CategoryQueryService categoryQueryService;

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "카테고리 조회 API", description = "DB에 저장된 카테고리들을 조회합니다.")
    public ApiResponse<CategoryResponse.CategoryListResponse> getCategoryList(){

        CategoryResponse.CategoryListResponse response = categoryQueryService.getCategoryList();

        return ApiResponse.of(SuccessStatus._OK, response);
    }
}
