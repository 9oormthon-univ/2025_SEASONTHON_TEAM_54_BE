package com.example.ssg_tab.domain.contents.service;

import com.example.ssg_tab.domain.contents.converter.ContentsConverter;
import com.example.ssg_tab.domain.contents.dto.response.ContentsResponse;
import com.example.ssg_tab.domain.contents.entity.Contents;
import com.example.ssg_tab.domain.contents.repository.ContentsRepository;
import com.example.ssg_tab.domain.user.entity.mapping.UserContents;
import com.example.ssg_tab.domain.user.repository.UserContentsRepository;
import com.example.ssg_tab.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentsQueryServiceImpl implements ContentsQueryService {

    private final ContentsRepository contentsRepository;
    private final UserContentsRepository userContentsRepository;

    @Override
    @Transactional(readOnly = true)
    public ContentsResponse.ContentsPageResponse getContentsPage(Integer page, Integer size, Long categoryId) {
        int pageIndex = (page == null || page < 0) ? 0 : page;
        int pageSize  = (size == null) ? 20 : Math.min(Math.max(size, 1), 50);
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        Page<Contents> pageData = (categoryId == null)
                ? contentsRepository.findAllWithCategories(pageable)
                : contentsRepository.findByCategoryIdWithCategories(categoryId, pageable);

        return ContentsConverter.toContentsPageResponse(pageData);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentsResponse.Bookmark getBookmark(Long userId, Long categoryId) {

        List<Contents> contents = userContentsRepository
                .findBookmarkedContents(userId, categoryId);

        return ContentsConverter.toBookmark(contents);
    }
}
