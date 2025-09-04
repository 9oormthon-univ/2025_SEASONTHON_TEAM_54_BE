package com.example.ssg_tab.domain.contents.repository;

import com.example.ssg_tab.domain.contents.entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Integer> {
    @Query("select c.sourceUrl from Contents c where c.sourceUrl in :urls")
    List<String> findExistingUrls(Collection<String> urls); // 원본 URL이 같은 것 찾기
}
