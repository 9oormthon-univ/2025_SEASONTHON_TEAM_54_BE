package com.example.ssg_tab.domain.user.repository;

import com.example.ssg_tab.domain.contents.entity.Contents;
import com.example.ssg_tab.domain.user.entity.mapping.UserContents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserContentsRepository extends JpaRepository<UserContents, Long> {
    @Query("""
        select distinct c
        from UserContents uc
        join uc.contents c
        left join c.categories cc
        left join cc.category cat
        where uc.user.id = :userId
          and (:categoryId is null or cat.id = :categoryId)
        """)
    List<Contents> findBookmarkedContents(@Param("userId") Long userId,
                                          @Param("categoryId") Long categoryId);
}
