package com.ssafy.a302.domain.badge.repository;

import com.ssafy.a302.domain.badge.entity.Badge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class BadgeInit {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {
    }

    /**
     * 사용할 때만 @Test 애노테이션을 활성화시켜주세요.
     */
    // @Test
    @Rollback(false)
    void badgeRegister() {
        String[][] badgeInfos = {
                {"가입 환영 뱃지", "welcome-badge", "png", "도와주개냥에 가입하신 모든분께 드리는 뱃지에요! 앞으로 좋은 활동 부탁드린다냥~", "최초 가입 시 뱃지 제공"},
                {"소통하는 활동가", "communication-activist", "png", "사람들과 봉사 이야기로 자주 소통하는 회원이 받는 뱃지에요!", "커뮤니티 글 10개 이상 작성 시 뱃지 획득"},
                {"행복의 시작", "start-of-happiness", "png", "유기동물을 입양한 당신! 이제 정말 행복이 가득할거에요!", "유기동물 입양 시 뱃지 획득"},
                {"행복은 제곱", "happiness-is-square", "png", "입양이 벌써 두번째시라구요?! 행복은 제곱이 될거에요!!", "유기동물 입양 2회시 뱃지 획득"},
                {"세심한 관찰꾼", "a-careful-observer", "png", "주위를 세심하게 관찰해 유기동물을 발견한 당신에게 드리는 상이에요!", "유기동물발견 신고 5회 이상 시 뱃지 획득 "},
                {"용기있는 발 딛음", "brave-step", "png", "첫 봉사활동을 완료한 당신! 유기동물을 위해 힘내줘서 고마워요!", "봉사활동 1회 참여 시(인증) 뱃지 획득"},
                {"나는 봉사 모집왕", "volunteer-recruitment-king", "png", "봉사 모집왕에게 드리는 뱃지에요!", "봉사 모집 5회 달성"},
                {"나는 봉사 모집왕2", "volunteer-recruitment-king2", "png", "봉사 모집왕에게 드리는 뱃지에요! ", "봉사 모집 15회 달성"},
                {"나는 봉사 참여왕", "volunteer-participation-king", "png", "봉사 참여왕에게 드리는 뱃지에요!", "봉사 참여 5회 달성"},
                {"나는 봉사 참여왕2", "volunteer-participation-king2", "png", "봉사 참여왕에게 드리는 뱃지에요!", "봉사 참여 15회 달성"},
        };

        final int NAME = 0;
        final int IMG_URL = 1;
        final int EXT = 2;
        final int CONTENT = 3;
        final int HOW_TO_GET = 4;

        for (String[] badgeInfo : badgeInfos) {
            badgeRepository.save(Badge.builder()
                    .name(badgeInfo[NAME])
                    .imageFilename(badgeInfo[IMG_URL] + "." + badgeInfo[EXT])
                    .content(badgeInfo[CONTENT])
                    .howToGet(badgeInfo[HOW_TO_GET])
                    .build());
        }
    }

    /**
     * 사용할 때만 @Test 애노테이션을 활성화시켜주세요.
     */
//    @Test
    @Rollback(false)
    void removeAllBadge() {
        badgeRepository.deleteAll();
        em.flush();
        em.clear();
    }
}