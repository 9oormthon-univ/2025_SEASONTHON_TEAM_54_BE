package com.example.ssg_tab.domain.contents.controller;

import com.example.ssg_tab.domain.contents.dto.request.ContentsRequest;
import com.example.ssg_tab.domain.contents.dto.response.ContentsResponse;
import com.example.ssg_tab.domain.contents.repository.ContentsRepository;
import com.example.ssg_tab.domain.contents.service.ContentsCommandService;
import com.example.ssg_tab.domain.contents.service.ContentsQueryService;
import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
@Tag(name = "Contents", description = "컨텐츠 관련 API")
public class ContentsController {

    private final ContentsQueryService contentsQueryService;
    private final ContentsCommandService contentsCommandService;

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "컨텐츠 조회 API",description = "홈에서 컨텐츠를 조회합니다.")
    public ApiResponse<ContentsResponse.ContentsPageResponse> getContentsPage(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Long categoryId
    ) {

        ContentsResponse.ContentsPageResponse response = contentsQueryService.getContentsPage(page, size, categoryId);

        return ApiResponse.of(SuccessStatus._OK, response);

    }

    @PostMapping(value = "/bookmark", produces = "application/json")
    @Operation(summary = "컨텐츠 좋아요 API", description = "하나의 컨텐츠에 좋아요(북마크)를 등록합니다.")
    public ApiResponse<Object> bookmark(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ContentsRequest.Bookmark request) {

        Long uid = Long.valueOf(userDetails.getUsername());   // 유저 ID
        contentsCommandService.bookmark(uid, request);

        return ApiResponse.of(SuccessStatus._OK, null);
    }

    @GetMapping(value = "/bookmark", produces = "application/json")
    @Operation(summary = "좋아요한 컨텐츠 조회 API", description = "사용자가 좋아요 버튼을 클릭하여 저장한 컨텐츠들을 조회합니다. 카테고리를 입력하지 않으면 전체 카테고리로 조회합니다.")
    public ApiResponse<ContentsResponse.Bookmark> getBookmark(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestParam(required = false) Long categoryId) {

        Long uid = Long.valueOf(userDetails.getUsername());   // 유저 ID
        ContentsResponse.Bookmark response = contentsQueryService.getBookmark(uid, categoryId);

        return ApiResponse.of(SuccessStatus._OK, response);
    }

}
