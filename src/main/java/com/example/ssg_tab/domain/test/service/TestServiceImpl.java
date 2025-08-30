package com.example.ssg_tab.domain.test.service;

import com.example.ssg_tab.domain.test.dto.request.TestRequest;
import com.example.ssg_tab.domain.test.dto.response.TestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    @Override
    public TestResponse.TestResponseDTO getTest(Long request) {

        //비즈니스 로직
        Long result = request+1;

        //테스트이므로 converter 코드 생략
        return TestResponse.TestResponseDTO.builder()
                .number(result)
                .build();
    }

    @Override
    public TestResponse.TestResponseDTO postTest(TestRequest.TestRequestDTO request) {

        //비즈니스 로직
        Long result = request.getNumber() + 1;

        return TestResponse.TestResponseDTO.builder()
                .number(result)
                .build();
    }
}
