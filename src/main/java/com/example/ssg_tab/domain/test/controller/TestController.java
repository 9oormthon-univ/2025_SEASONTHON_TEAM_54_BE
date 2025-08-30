package com.example.ssg_tab.domain.test.controller;

import com.example.ssg_tab.domain.test.dto.request.TestRequest;
import com.example.ssg_tab.domain.test.dto.response.TestResponse;
import com.example.ssg_tab.domain.test.service.TestService;
import com.example.ssg_tab.global.apiPayload.ApiResponse;
import com.example.ssg_tab.global.apiPayload.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Tag(name = "Test", description = "테스트 API")
public class TestController {

    private final TestService testService;

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
}
