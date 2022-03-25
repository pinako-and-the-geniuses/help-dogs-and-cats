package com.ssafy.a302.domain.member.repository;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.global.message.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

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
    @DisplayName("기본키로 회원 엔티티 조회")
    void findMemberBySeq() {
        Member savedMember = memberRepository.save(member1);
        em.flush();
        em.clear();

        /**
         * 기본키로 엔티티 조회
         */
        Member findMember = memberRepository.findMemberBySeq(savedMember.getSeq())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MEMBER_SEQ));

        /**
         * 동등성 비교
         */
        assertThat(findMember)
                .usingRecursiveComparison()
                .ignoringFields("detail.member.createdDate", "detail.member.lastModifiedDate", "createdDate", "lastModifiedDate")
                .isEqualTo(member1);

        /**
         * 삭제 처리
         */
        findMember.delete();

        /**
         * 삭제 처리 후에 null이 반환되는지 확인한다.
         */
        assertThatThrownBy(() -> memberRepository.findMemberBySeq(savedMember.getSeq())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MEMBER_SEQ)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_MEMBER_SEQ);
    }

    @Test
    @DisplayName("이메일로 회원 엔티티 조회")
    void findMemberByEmail() {
        Member savedMember = memberRepository.save(member1);
        em.flush();
        em.clear();

        Member findMember = memberRepository.findMemberByEmail(savedMember.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER_EMAIL));

        /**
         * 동등성 비교
         */
        assertThat(findMember)
                .usingRecursiveComparison()
                .ignoringFields("detail.member.createdDate", "detail.member.lastModifiedDate", "createdDate", "lastModifiedDate")
                .isEqualTo(member1);

        /**
         * 삭제 처리
         */
        findMember.delete();

        /**
         * 삭제 처리 후에 null이 반환되는지 확인한다.
         */
        assertThatThrownBy(() -> memberRepository.findMemberByEmail(savedMember.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_MEMBER);
    }
}