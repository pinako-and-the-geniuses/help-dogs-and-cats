package com.ssafy.a302.domain.badge.service;

import com.ssafy.a302.domain.adopt.repository.AdoptAuthRepository;
import com.ssafy.a302.domain.badge.constant.BadgeName;
import com.ssafy.a302.domain.badge.entity.Badge;
import com.ssafy.a302.domain.badge.entity.MemberBadge;
import com.ssafy.a302.domain.badge.repository.BadgeRepository;
import com.ssafy.a302.domain.badge.repository.MemberBadgeRepository;
import com.ssafy.a302.domain.community.repository.CommunityRepository;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerParticipantRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    private final MemberBadgeRepository memberBadgeRepository;

    private final MemberRepository memberRepository;

    private final CommunityRepository communityRepository;

    private final AdoptAuthRepository adoptAuthRepository;

    private final VolunteerParticipantRepository volunteerParticipantRepository;

    private final VolunteerRepository volunteerRepository;

    @Override
    public boolean isQualifiedWelcomeBadge(Long memberSeq) {
        /**
         * 화원 가입에 성공한 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.WELCOME_BADGE);
        if (hasBadge) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveWelcomeBadge(Long memberSeq) {
        if (isQualifiedWelcomeBadge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.WELCOME_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    @Override
    public boolean isQualifiedCommunicationActivistBadge(Long memberSeq) {
        /**
         * 뱃지를 보유하지 않았고
         * 커뮤니티 게시글 개수가 10개인 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.COMMUNICATION_ACTIVIST_BADGE);
        if (hasBadge) {
            return false;
        }

        Integer count = communityRepository.countAllByMemberSeq(memberSeq);
        if (count != 10) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveCommunicationActivistBadge(Long memberSeq) {
        if (isQualifiedCommunicationActivistBadge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.COMMUNICATION_ACTIVIST_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    @Override
    public boolean isQualifiedStartOfHappinessBadge(Long memberSeq) {
        /**
         * 뱃지를 보유하지 않았고
         * 입양 인증 성공 횟수가 1회인 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.START_OF_HAPPINESS_BADGE);
        if (hasBadge) {
            return false;
        }

        Integer count = adoptAuthRepository.countAllByMemberSeq(memberSeq);
        if (count != 1) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveStartOfHappinessBadge(Long memberSeq) {
        if (isQualifiedStartOfHappinessBadge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.START_OF_HAPPINESS_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    @Override
    public boolean isQualifiedHappinessIsSquareBadge(Long memberSeq) {
        /**
         * 뱃지를 보유하지 않았고,
         * 입양 인증 횟수가 2회인 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.HAPPINESS_IS_SQUARE_BADGE);
        if (hasBadge) {
            return false;
        }

        Integer count = adoptAuthRepository.countAllByMemberSeq(memberSeq);
        if (count != 2) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveHappinessIsSquareBadge(Long memberSeq) {
        if (isQualifiedHappinessIsSquareBadge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.HAPPINESS_IS_SQUARE_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    @Override
    public boolean isQualifiedACarefulObserverBadge(Long memberSeq) {
        /**
         * 뱃지를 보유하지 않았고,
         * 커뮤니티에 제보 글이 5개인 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.A_CAREFUL_OBSERVER_BADGE);
        if (hasBadge) {
            return false;
        }

        Integer count = communityRepository.countAllByMemberSeqAndCategoryEqReport(memberSeq);
        if (count != 5) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveACarefulObserverBadge(Long memberSeq) {
        if (isQualifiedACarefulObserverBadge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.A_CAREFUL_OBSERVER_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    @Override
    public boolean isQualifiedBraveStepBadge(Long memberSeq) {
        /**
         * 뱃지를 보유하지 않았고,
         * 봉사활동 인증 횟수가 1회인 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.BRAVE_STEP_BADGE);
        if (hasBadge) {
            return false;
        }

        Integer count = volunteerParticipantRepository.countByMemberSeqAndVolunteerStatusEqDone(memberSeq);
        if (count != 1) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveBraveStepBadge(Long memberSeq) {
        if (isQualifiedBraveStepBadge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.BRAVE_STEP_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    @Override
    public boolean isQualifiedVolunteerRecruitmentKingBadge(Long memberSeq) {
        /**
         * 뱃지를 보유하지 않았고,
         * 봉사활동 모집 횟수가 5회인 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.VOLUNTEER_RECRUITMENT_KING_BADGE);
        if (hasBadge) {
            return false;
        }

        Integer count = volunteerRepository.countByMemberSeqAndVolunteerStatusEqDone(memberSeq);
        if (count != 5) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveVolunteerRecruitmentKingBadge(Long memberSeq) {
        if (isQualifiedVolunteerRecruitmentKingBadge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.VOLUNTEER_RECRUITMENT_KING_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    @Override
    public boolean isQualifiedVolunteerRecruitmentKing2Badge(Long memberSeq) {
        /**
         * 뱃지를 보유하지 않았고,
         * 봉사활동 모집 횟수가 15회인 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.VOLUNTEER_RECRUITMENT_KING2_BADGE);
        if (hasBadge) {
            return false;
        }

        Integer count = volunteerRepository.countByMemberSeqAndVolunteerStatusEqDone(memberSeq);
        if (count != 15) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveVolunteerRecruitmentKing2Badge(Long memberSeq) {
        if (isQualifiedVolunteerRecruitmentKing2Badge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.VOLUNTEER_RECRUITMENT_KING2_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    @Override
    public boolean isQualifiedVolunteerParticipationKingBadge(Long memberSeq) {
        /**
         * 뱃지를 보유하지 않았고,
         * 봉사활동 인증 횟수가 5회인 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.VOLUNTEER_PARTICIPATION_KING_BADGE);
        if (hasBadge) {
            return false;
        }

        Integer count = volunteerParticipantRepository.countByMemberSeqAndVolunteerStatusEqDone(memberSeq);
        if (count != 5) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveVolunteerParticipationKingBadge(Long memberSeq) {
        if (isQualifiedVolunteerParticipationKingBadge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.VOLUNTEER_PARTICIPATION_KING_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    @Override
    public boolean isQualifiedVolunteerParticipationKing2Badge(Long memberSeq) {
        /**
         * 뱃지를 보유하지 않았고,
         * 봉사활동 인증 횟수가 15회인 경우
         */
        boolean hasBadge = hasBadge(memberSeq, BadgeName.VOLUNTEER_PARTICIPATION_KING2_BADGE);
        if (hasBadge) {
            return false;
        }

        Integer count = volunteerParticipantRepository.countByMemberSeqAndVolunteerStatusEqDone(memberSeq);
        if (count != 15) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void approveVolunteerParticipationKing2Badge(Long memberSeq) {
        if (isQualifiedVolunteerParticipationKing2Badge(memberSeq)) {
            approveBadge(memberSeq, BadgeName.VOLUNTEER_PARTICIPATION_KING2_BADGE);
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_BADGE_APPROVE);
    }

    private boolean hasBadge(Long memberSeq, String badgeName) {
        return memberBadgeRepository.existsByMemberSeqAndBadgeName(memberSeq, badgeName);
    }

    private void approveBadge(Long memberSeq, String badgeName) {
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));
        Badge findBadge = badgeRepository.findByName(badgeName)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        memberBadgeRepository.save(MemberBadge.builder()
                .member(findMember)
                .badge(findBadge)
                .build());
    }
}
