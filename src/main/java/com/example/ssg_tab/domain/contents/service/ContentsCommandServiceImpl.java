package com.example.ssg_tab.domain.contents.service;

import com.example.ssg_tab.domain.contents.dto.request.ContentsRequest;
import com.example.ssg_tab.domain.contents.entity.Contents;
import com.example.ssg_tab.domain.contents.repository.ContentsRepository;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.domain.user.entity.mapping.UserContents;
import com.example.ssg_tab.domain.user.repository.UserContentsRepository;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentsCommandServiceImpl implements ContentsCommandService {

    private final UserRepository userRepository;
    private final ContentsRepository contentsRepository;
    private final UserContentsRepository userContentsRepository;

    @Override
    @Transactional
    public void bookmark(Long userId, ContentsRequest.BookmarkRequest request) {

        // 1. 사용자 및 컨텐츠 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        Contents contents = contentsRepository.findById(request.getContentsId()).orElseThrow(() -> new GeneralException(ErrorStatus.CONTENTS_NOT_FOUND));

        // 2. 사용자와 컨텐츠 연관관계 생성
        UserContents bookmark = UserContents.builder()
                .user(user)
                .contents(contents)
                .build();

        userContentsRepository.save(bookmark);

    }
}
