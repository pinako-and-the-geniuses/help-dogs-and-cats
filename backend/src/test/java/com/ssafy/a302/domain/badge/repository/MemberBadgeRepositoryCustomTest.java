package com.ssafy.a302.domain.badge.repository;

import com.ssafy.a302.domain.badge.entity.Badge;
import com.ssafy.a302.domain.badge.entity.MemberBadge;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberBadgeRepositoryCustomTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberBadgeRepository memberBadgeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    private Member member1;

    private Badge badge1, badge2;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        badgeRepository.deleteAll();
        em.flush();
        em.clear();

        member1 = Member.builder()
                .email("test1@test.com")
                .password("password1")
                .role(Member.Role.MEMBER)
                .build();

        badge1 = Badge.builder()
                .name("name1")
                .content("content1")
                .howToGet("howToGet1")
                .imageFilename("imageFilename1")
                .build();

        badge2 = Badge.builder()
                .name("name2")
                .content("content\2")
                .howToGet("howToGet2")
                .imageFilename("imageFilename2")
                .build();
    }

    @Test
    @DisplayName("회원 외래키로 뱃지 목록 조회 - 성공")
    void findBadgeAllByMemberSeqSuccess() {
        Member savedMember = memberRepository.save(member1);

        List<Badge> nullList = memberBadgeRepository.findBadgeAllByMemberSeq(savedMember.getSeq())
                .orElse(null);
        assertThat(nullList).isNull();

        /**
         * 테스트용 데이터 세팅
         */
        List<Badge> badges = List.of(this.badge1, badge2);
        badgeRepository.saveAll(badges);

        for (Badge badge : badges) {
            memberBadgeRepository.save(MemberBadge.builder()
                    .badge(badge)
                    .member(savedMember)
                    .build());
        }
        em.flush();
        em.clear();

        /**
         * 데이터 검증
         */
        List<Badge> findBadges = memberBadgeRepository.findBadgeAllByMemberSeq(savedMember.getSeq())
                .orElse(null);
        assertThat(findBadges).isNotNull();
        assertThat(findBadges.size()).isEqualTo(badges.size());
        for (int i = 0; i < findBadges.size(); i++) {
            Badge findBadge = findBadges.get(i);
            Badge badge = badges.get(i);
            assertThat(findBadge)
                    .usingRecursiveComparison()
                    .ignoringFields("memberBadges", "createdDate")
                    .isEqualTo(badge);
        }
    }
}