package com.ssafy.a302.domain.badge.entity;

import com.ssafy.a302.global.entity.base.BaseCreatedEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "tb_badge"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "name", "imgUrl", "content", "howToGet"})
public class Badge extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "badge_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imgUrl;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String howToGet;

    @OneToMany(mappedBy = "badge", cascade = ALL)
    private List<MemberBadge> memberBadges = new ArrayList<>();

    @Builder
    public Badge(String name, String imgUrl, String content, String howToGet) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.content = content;
        this.howToGet = howToGet;
    }
}
