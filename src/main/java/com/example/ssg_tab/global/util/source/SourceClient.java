package com.example.ssg_tab.global.util.source;

import java.time.Duration;

import java.util.List;


public interface SourceClient {
    List<ContentsItem> fetchTopN(String query, Duration timeout);
}

