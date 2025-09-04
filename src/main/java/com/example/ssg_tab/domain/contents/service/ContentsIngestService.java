package com.example.ssg_tab.domain.contents.service;

import com.example.ssg_tab.domain.contents.entity.Contents;

import java.util.List;

public interface ContentsIngestService {
    List<Contents> fetchAndStore(Long categoryId);
}