package com.example.ssg_tab.domain.test.controller;

import com.example.ssg_tab.domain.test.dto.request.TestRequest;
import com.example.ssg_tab.domain.test.dto.response.TestResponse;
import com.example.ssg_tab.domain.test.service.StorageService;
import com.example.ssg_tab.domain.test.service.TestService;
import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Tag(name = "Test", description = "테스트 API")
public class TestController {

    private final TestService testService;
    private final StorageService storageService;

    @GetMapping(value = "/get", produces = "application/json")
    @Operation(summary = "GET 테스트 API", description = "get 방식의 테스트 api 설명입니다.")
    public ApiResponse<TestResponse.TestResponseDTO> getTest(@RequestParam("number") Long number){

        TestResponse.TestResponseDTO response = testService.getTest(number);

        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @PostMapping(value = "/post", produces = "application/json")
    @Operation(summary = "POST 테스트 API", description = "post 방식의 테스트 api 설명입니다.")
    public ApiResponse<TestResponse.TestResponseDTO> postTest(@Valid @RequestBody TestRequest.TestRequestDTO request){

        TestResponse.TestResponseDTO response = testService.postTest(request);

        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @GetMapping("/storage")
    @Operation(summary = "Azure Storage 테스트 API", description = "Azure Storage의 'test' 컨테이너에 있는 Blob 목록을 가져옵니다.")
    public ApiResponse<List<String>> listBlobs() {
        List<String> response = storageService.listBlobsInContainer("test");
        return ApiResponse.of(SuccessStatus._OK, response);
    }
}
