package com.example.ssg_tab.domain.test.service;

import com.example.ssg_tab.domain.test.dto.request.TestRequest;
import com.example.ssg_tab.domain.test.dto.response.TestResponse;

public interface TestService {
    TestResponse.TestResponseDTO getTest(Long request);
    TestResponse.TestResponseDTO postTest(TestRequest.TestRequestDTO request);
}
