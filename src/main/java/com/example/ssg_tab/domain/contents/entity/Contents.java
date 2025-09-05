package com.example.ssg_tab.domain.contents.entity;

import com.example.ssg_tab.domain.contents.entity.mapping.ContentsCategory;
import com.example.ssg_tab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "contents")
public class Contents extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "title")
    private String title;       // 제목

    @Column(name = "body", length = 10000)
    @Lob
    private String body;        // 본문 내용

    @Column(name = "source_url", unique = true)
    private String sourceUrl;   // 본문 URL

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition="json")
    Map<String,Object> meta; // 소스별 세부(최소화)

    /**
     * 연관 관계
     **/
    @OneToMany(mappedBy = "contents", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ContentsCategory> categories = new ArrayList<>();

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
