package com.example.ssg_tab.domain.contents.service;

import com.example.ssg_tab.domain.contents.dto.request.ContentsRequest;

public interface ContentsCommandService {
    void bookmark(Long userId, ContentsRequest.Bookmark request);
}
