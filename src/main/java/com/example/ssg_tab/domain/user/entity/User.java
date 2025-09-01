package com.example.ssg_tab.domain.user.entity;

import com.example.ssg_tab.domain.user.entity.enums.UserRole;
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
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long socialId;

    // user 기본 정보
    @Column(name = "nickname", length = 100)
    private String nickname;
    @Column(name = "email", unique = true, length = 255)
    private String email;
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

}
