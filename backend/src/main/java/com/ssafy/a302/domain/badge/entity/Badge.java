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
        name = "tb_badge",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "name", "imageFilename", "content", "howToGet"})
public class Badge extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "badge_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String howToGet;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imageFilename;

    @OneToMany(mappedBy = "badge", cascade = ALL, orphanRemoval = true)
    private List<MemberBadge> memberBadges = new ArrayList<>();

    @Builder
    public Badge(String name, String content, String howToGet, String imageFilename) {
        this.name = name;
        this.content = content;
        this.howToGet = howToGet;
        this.imageFilename = imageFilename;
    }
}
