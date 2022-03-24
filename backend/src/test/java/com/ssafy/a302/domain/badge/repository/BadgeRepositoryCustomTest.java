package com.ssafy.a302.domain.badge.repository;

import com.ssafy.a302.domain.badge.entity.Badge;
import com.ssafy.a302.domain.badge.service.dto.BadgeDto;
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
class BadgeRepositoryCustomTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Badge badge1, badge2;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        badgeRepository.deleteAll();
        em.flush();
        em.clear();

        badge1 = Badge.builder()
                .name("name1")
                .content("content1")
                .imageFilename("imageFilename1")
                .howToGet("howToGet1")
                .build();

        badge2 = Badge.builder()
                .name("name2")
                .content("content2")
                .imageFilename("imageFilename2")
                .howToGet("howToGet2")
                .build();
    }

    @Test
    @DisplayName("뱃지 목록(ResponseDto) 조회 - 성공")
    void findBadgeDtoResponseSuccess() {
        List<BadgeDto.ForProfile> nullList = badgeRepository.findBadgesForProfileDto()
                .orElse(null);

        assertThat(nullList).isNull();

        /**
         * 테스트용 데이터 세팅
         */
        List<Badge> badges = List.of(badge1, badge2);
        badgeRepository.saveAll(badges);
        em.flush();
        em.clear();

        /**
         * 데이터 검증
         */
        List<BadgeDto.ForProfile> findBadgesForProfile = badgeRepository.findBadgesForProfileDto()
                .orElse(null);
        assertThat(findBadgesForProfile).isNotNull();
        assertThat(findBadgesForProfile.size()).isEqualTo(badges.size());

        for (int i = 0; i < findBadgesForProfile.size(); i++) {
            BadgeDto.ForProfile findBadgeForProfile = findBadgesForProfile.get(i);
            Badge badge = badges.get(i);
            assertThat(findBadgeForProfile.getName()).isEqualTo(badge.getName());
            assertThat(findBadgeForProfile.getContent()).isEqualTo(badge.getContent());

            String imageFilePath = findBadgeForProfile.getImageFilePath();
            assertThat(imageFilePath).isEqualTo("static/images/badge/" + badge.getImageFilename());

            int pos = imageFilePath.lastIndexOf("/");
            assertThat(imageFilePath.substring(pos + 1)).isEqualTo(badge.getImageFilename());
            assertThat(findBadgeForProfile.getHowToGet()).isEqualTo(badge.getHowToGet());
        }
    }
}