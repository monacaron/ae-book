package com.c201.book.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kakao_id", length = 200)
    private Long kakaoId;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "phone", length = 100)
    private String phone;

    @Column(name = "profile_url", length = 200)
    // @Column(name = "profile_url", length = 200, columnDefinition = "카카오에서 주는 url")
    private String profileUrl;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT default 1")
    private Boolean status;

    @Builder
    public User(Long kakaoId, String nickname, String phone, String profileUrl, Boolean status) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.phone = phone;
        this.profileUrl = profileUrl;
        this.status = status;
    }
}