package com.example.ssg_tab.domain.user.entity.mapping;

import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.user.entity.User;
import com.example.ssg_tab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_category")
public class UserCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // 유저의 해당 카테고리 진행률
    @Builder.Default
    @Column(name = "progress", nullable = false)
    private int progress = 0;

    public void setUser(User user) {
        this.user = user;
    }

    public void updateProgress(int progress) {
        this.progress = progress;
    }
    
}
