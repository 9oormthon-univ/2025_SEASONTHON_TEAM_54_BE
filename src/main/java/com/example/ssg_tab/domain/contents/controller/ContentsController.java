package com.example.ssg_tab.domain.contents.controller;

import com.example.ssg_tab.domain.contents.entity.Contents;
import com.example.ssg_tab.domain.contents.service.ContentsIngestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev")
public class ContentsController {

    private final ContentsIngestService contentsIngestService;

    @GetMapping("/ingest")
    public List<Contents> ingest(
            @RequestParam Long categoryId
    ){
        return contentsIngestService.fetchAndStore(categoryId);
    }

}
