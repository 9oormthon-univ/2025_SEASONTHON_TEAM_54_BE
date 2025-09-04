package com.example.ssg_tab.domain.contents.service;

import com.example.ssg_tab.domain.contents.entity.Contents;

import java.util.List;

public interface ContentsCreateService {
    List<Contents> fetchAndStore(Long categoryId);
}