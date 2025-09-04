package com.example.ssg_tab.domain.contents.service;

import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.category.repository.CategoryRepository;
import com.example.ssg_tab.domain.contents.entity.Contents;
import com.example.ssg_tab.domain.contents.entity.mapping.ContentsCategory;
import com.example.ssg_tab.domain.contents.repository.ContentsRepository;
import com.example.ssg_tab.global.apiPayload.status.ErrorStatus;
import com.example.ssg_tab.global.exception.GeneralException;
import com.example.ssg_tab.global.util.source.ContentsItem;
import com.example.ssg_tab.global.util.source.SourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsCreateServiceImpl implements ContentsCreateService {

    private final List<SourceClient> clients;         // NaverNewsClient, YouthCenterClient 자동 주입
    private final ContentsRepository contentsRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Contents> fetchAndStore(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._BAD_REQUEST));
        String query = category.getName();

        List<ContentsItem> merged = new ArrayList<>();
        for (SourceClient c : clients) {
            merged.addAll(c.fetchTopN(query, Duration.ofMillis(1500)));
        }

        Map<String, ContentsItem> byUrl = new LinkedHashMap<>();
        for (ContentsItem it : merged) {
            if (it.sourceUrl() != null) byUrl.putIfAbsent(it.sourceUrl(), it);
        }
        List<ContentsItem> deduped = new ArrayList<>(byUrl.values());
        if (deduped.isEmpty()) return List.of();

        List<String> urls = deduped.stream().map(ContentsItem::sourceUrl).toList();
        Set<String> exists = new HashSet<>(contentsRepository.findExistingUrls(urls));
        List<ContentsItem> newOnes = deduped.stream()
                .filter(it -> !exists.contains(it.sourceUrl()))
                .limit(200)
                .toList();

        // 카테고리 링크까지 생성
        List<Contents> toSave = newOnes.stream()
                .map(it -> map(it, category))
                .toList();

        contentsRepository.saveAll(toSave);
        return toSave.stream().limit(10).collect(Collectors.toList());
    }

    private Contents map(ContentsItem x, Category category) {
        Contents contents = Contents.builder()
                .title(x.title())
                .imageUrl(x.imageUrl())
                .sourceUrl(x.sourceUrl())
                .body(x.body())
                .build();

        ContentsCategory link = new ContentsCategory();
        link.setContents(contents);
        link.setCategory(category);

        contents.getCategories().add(link);
        return contents;
    }

}
