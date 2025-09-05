package com.example.ssg_tab.domain.contents.controller;

import com.example.ssg_tab.domain.contents.dto.response.ContentsResponse;
import com.example.ssg_tab.domain.contents.repository.ContentsRepository;
import com.example.ssg_tab.domain.contents.service.ContentsQueryService;
import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
@Tag(name = "Contents", description = "컨텐츠 관련 API")
public class ContentsController {

    private final ContentsQueryService contentsQueryService;

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "컨텐츠 조회 API",description = "홈에서 컨텐츠를 조회합니다.")
    public ApiResponse<ContentsResponse.ContentsPageResponse> getContentsPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Long categoryId
    ) {

        Long uid = Long.valueOf(userDetails.getUsername());
        ContentsResponse.ContentsPageResponse response = contentsQueryService.getContentsPage(page, size, categoryId);

        return ApiResponse.of(SuccessStatus._OK, response);

    }


}
