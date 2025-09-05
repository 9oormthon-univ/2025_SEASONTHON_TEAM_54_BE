package com.example.ssg_tab.domain.contents.repository;

import com.example.ssg_tab.domain.contents.entity.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
    @Query("select c.sourceUrl from Contents c where c.sourceUrl in :urls")
    List<String> findExistingUrls(Collection<String> urls); // 원본 URL이 같은 것 찾기

    // 전체 조회 (카테고리까지 한 번에 로딩) — N+1 방지
    @EntityGraph(attributePaths = {"categories", "categories.category"})
    @Query("select c from Contents c order by c.createdAt desc")
    Page<Contents> findAllWithCategories(Pageable pageable);

    // categoryId로 필터 — distinct로 중복 제거
    @EntityGraph(attributePaths = {"categories", "categories.category"})
    @Query("""
        select distinct c
        from Contents c
        join c.categories cc
        join cc.category cat
        where cat.id = :categoryId
        order by c.createdAt desc
    """)
    Page<Contents> findByCategoryIdWithCategories(Long categoryId, Pageable pageable);
}
