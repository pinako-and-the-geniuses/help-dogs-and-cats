package com.ssafy.a302.domain.member.repository;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryCustomTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Member member1;

    private MemberDetail detail1;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        em.flush();
        em.clear();

        member1 = Member.builder()
                .email("test1@test.com")
                .password("pass12#$")
                .role(Member.Role.MEMBER)
                .build();

        detail1 = MemberDetail.builder()
                .member(member1)
                .nickname("good1")
                .tel("010-0001-0001")
                .activityArea("서울시 강남구")
                .build();
    }

    @Test
    @DisplayName("회원 DTO 조회 - 성공")
    void findMemberDtoBySeqSuccess() {
        Member savedMember = memberRepository.save(member1);
        em.flush();
        em.clear();

        /**
         * memberDto 데이터를 찾아돈다.
         */
        MemberDto findMemberDto = memberRepository.findMemberDtoBySeq(savedMember.getSeq())
                .orElse(null);

        MemberDetail detail = member1.getDetail();

        /**
         * 데이터 검증
         */
        assertThat(findMemberDto).isNotNull();
        assertThat(findMemberDto.getEmail()).isEqualTo(member1.getEmail());
        assertThat(findMemberDto.getPassword()).isEqualTo(member1.getPassword());
        assertThat(findMemberDto.getRole()).isEqualTo(member1.getRole());
        assertThat(findMemberDto.getNickname()).isEqualTo(detail.getNickname());
        assertThat(findMemberDto.getTel()).isEqualTo(detail.getTel());
        assertThat(findMemberDto.getActivityArea()).isEqualTo(detail.getActivityArea());
        assertThat(findMemberDto.getExp()).isEqualTo(detail.getExp());
        assertThat(findMemberDto.getProfileImageFilename()).isNull();
    }

}