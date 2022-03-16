package com.ssafy.a302.domain.member.entity;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(
        name = "tb_level",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "value"),
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "value", "content"})
public class Level {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "level_seq", columnDefinition = "INT UNSIGNED")
        private Long seq;

        @Column(nullable = false, columnDefinition = "INT UNSIGNED")
        private Integer value;

        @Column(nullable = false)
        private String content;

        @OneToMany(mappedBy = "level", cascade = ALL)
        private List<MemberDetail> memberDetails = new ArrayList<>();
}
