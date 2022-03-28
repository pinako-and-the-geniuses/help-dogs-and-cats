package com.ssafy.a302.domain.adopt.service;

import com.ssafy.a302.domain.adopt.controller.dto.AdoptAuthRequestDto;
import com.ssafy.a302.domain.adopt.entity.AdoptAuth;
import com.ssafy.a302.domain.adopt.repository.AdoptAuthRepository;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.global.enums.Status;
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
class AdoptServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdoptService adoptService;

    @Autowired
    private AdoptAuthRepository adoptAuthRepository;

    private Member member1;

    private MemberDetail detail1;

    private AdoptAuthRequestDto.RequestAdoptAuthInfo requestAdoptAuthInfo1, requestAdoptAuthInfo2;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        adoptAuthRepository.deleteAll();
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

        requestAdoptAuthInfo1 = AdoptAuthRequestDto.RequestAdoptAuthInfo.builder()
                .title("입양했습니다.")
                .content("얼마전에 입양했어요.")
                .build();

        requestAdoptAuthInfo1 = AdoptAuthRequestDto.RequestAdoptAuthInfo.builder()
                .title("또 입양했어요.")
                .content("그저께 입양했어요.")
                .build();
    }

    @Test
    @DisplayName("입양 인증 요청 - 성공")
    void requestAdoptAuthSuccess() {
        /**
         * 테스트 데이터 세팅
         */
        Member savedMember1 = memberRepository.save(member1);

        /**
         * 인증 요청 서비스 메서드 호출
         */
        Long savedAdoptAuthSeq = adoptService.requestAdoptAuth(savedMember1.getSeq(), requestAdoptAuthInfo1.toServiceDto());

        /**
         * 입양 인증 엔티티 조회
         */
        AdoptAuth savedAdoptAuth1 = adoptAuthRepository.findBySeq(savedAdoptAuthSeq)
                .orElse(null);

        /**
         * 데이터 검증
         */
        assertThat(savedAdoptAuth1).isNotNull();
        assertThat(savedAdoptAuth1.getTitle()).isEqualTo(requestAdoptAuthInfo1.getTitle());
        assertThat(savedAdoptAuth1.getContent()).isEqualTo(requestAdoptAuthInfo1.getContent());
        assertThat(savedAdoptAuth1.getStatus()).isEqualTo(Status.REQUEST);
    }
}