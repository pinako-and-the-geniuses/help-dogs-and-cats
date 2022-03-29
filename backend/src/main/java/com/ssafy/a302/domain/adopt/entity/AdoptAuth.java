package com.ssafy.a302.domain.adopt.entity;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.adopt.service.dto.AdoptAuthDto;
import com.ssafy.a302.domain.adopt.service.dto.AdoptDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.global.entity.base.BaseLastModifiedEntity;
import com.ssafy.a302.global.enums.Status;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(
        name = "tb_adopt_auth"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"seq", "title", "content", "status"})
public class AdoptAuth extends BaseLastModifiedEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "adopt_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    public AdoptAuth(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.status = Status.REQUEST;
        this.member = member;
    }

    public void modify(AdoptDto.AdoptAuth adoptAuth) {
        this.title = adoptAuth.getTitle();
        this.content= adoptAuth.getContent();
    }

    public AdoptDto.AdoptAuth toDto() {
        return AdoptDto.AdoptAuth.builder()
                .title(title)
                .content(content)
                .build();
    }

    public AdoptAuthDto.Response toResponseDto() {
        return AdoptAuthDto.Response.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .memberSeq(member.getSeq())
                .build();
    }
}
