package com.ssafy.a302.domain.community.repository;

import com.ssafy.a302.domain.community.entity.Community;
import com.ssafy.a302.domain.community.entity.CommunityComment;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
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
class CommunityCommentRepositoryCustomTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private CommunityCommentRepository communityCommentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommunityRepository communityRepository;

    private Community community1, community2;

    private Member member1, member2;

    private MemberDetail detail1, detail2;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        communityCommentRepository.deleteAll();
        communityRepository.deleteAll();
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

        member2 = Member.builder()
                .email("test2@test.com")
                .password("pass12#$")
                .role(Member.Role.MEMBER)
                .build();

        detail2 = MemberDetail.builder()
                .member(member2)
                .nickname("good2")
                .tel("010-0001-0002")
                .activityArea("서울시 강남구")
                .build();

        community1 = Community.builder()
                .title("입양 후기에요")
                .content("<p>입양했습니다.</p>")
                .category(Community.Category.REVIEW)
                .member(member1)
                .build();

        community2 = Community.builder()
                .title("유기동물 제보합니다")
                .content("<p>유기동물 제보해요</p>")
                .category(Community.Category.REPORT)
                .member(member2)
                .build();
    }

    @Test
    @DisplayName("댓글 엔티티 리스트 조회 - 성공")
    void findCommentsByCommunitySeqSuccess() {
        /**
         * 테스트 데이터 세팅
         */
        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);
        Community savedCommunity1 = communityRepository.save(community1);
        em.flush();
        em.clear();

        List<CommunityComment> comments0 = communityCommentRepository.findCommentsByCommunitySeq(savedCommunity1.getSeq())
                .orElse(null);
        assertThat(comments0).isNull();

        /**
         * 부모 댓글 들옥
         */
        CommunityComment parentComment = CommunityComment.builder()
                .content("부모 댓글")
                .community(savedCommunity1)
                .member(savedMember2)
                .build();
        CommunityComment savedParentComment = communityCommentRepository.save(parentComment);

        /**
         * 대댓글 등록
         */
        CommunityComment childComment = CommunityComment.builder()
                .content("대댓글")
                .community(savedCommunity1)
                .member(savedMember2)
                .build();
        childComment.createParent(savedParentComment);
        CommunityComment savedChildComment = communityCommentRepository.save(childComment);
        em.flush();
        em.clear();

        /**
         * 댓글 리스트 조회
         */
        List<CommunityComment> findComments = communityCommentRepository.findCommentsByCommunitySeq(savedCommunity1.getSeq())

        /**
         * 데이터 검증
         */
                .orElse(null);
        assertThat(findComments).isNotNull();
        assertThat(findComments.size()).isEqualTo(2);

        /**
         * 부모 댓글 검증
         */
        CommunityComment findParentComment = findComments.get(0);
        assertThat(findParentComment.getParent()).isNull();
        assertThat(findParentComment.getContent()).isEqualTo(savedParentComment.getContent());

        /**
         * 대댓글 검증
         */
        CommunityComment findChildComment = findParentComment.getChildren().get(0);
        assertThat(findChildComment.getParent().getSeq()).isEqualTo(savedParentComment.getSeq());
        assertThat(findChildComment.getContent()).isEqualTo(savedChildComment.getContent());
    }
}