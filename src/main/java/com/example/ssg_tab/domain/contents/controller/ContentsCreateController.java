package com.example.ssg_tab.domain.contents.controller;

import com.example.ssg_tab.domain.contents.entity.Contents;
import com.example.ssg_tab.domain.contents.service.ContentsCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev")
@Tag(name = "Contents-Dev", description = "백엔드용 컨텐츠 관련 컨트롤러 - 프론트 사용 x")
public class ContentsCreateController {

    private final ContentsCreateService contentsCreateService;

    @GetMapping("/create")
    @Operation(description = "카테고리 아이디 입력 -> 해당 카테고리의 이름으로 외부 API 호출 후 콘텐츠 생성")
    public List<Contents> ingest(
            @RequestParam Long categoryId
    ){
        return contentsCreateService.fetchAndStore(categoryId);
    }

}
