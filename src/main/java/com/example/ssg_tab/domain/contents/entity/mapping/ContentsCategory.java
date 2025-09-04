package com.example.ssg_tab.domain.contents.entity.mapping;

import com.example.ssg_tab.domain.category.entity.Category;
import com.example.ssg_tab.domain.contents.entity.Contents;
import com.example.ssg_tab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "contents_category")
public class ContentsCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", nullable = false)
    private Contents contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
