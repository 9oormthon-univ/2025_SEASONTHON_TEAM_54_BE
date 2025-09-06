package com.example.ssg_tab.domain.contents.service;

import com.example.ssg_tab.domain.contents.dto.response.ContentsResponse;

public interface ContentsQueryService {
    ContentsResponse.ContentsPageResponse getContentsPage(Integer page, Integer size, Long categoryId);
    ContentsResponse.Bookmark getBookmark(Long userId, Long categoryId);
}
