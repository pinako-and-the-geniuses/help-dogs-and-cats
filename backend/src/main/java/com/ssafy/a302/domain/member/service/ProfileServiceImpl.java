package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.badge.entity.Badge;
import com.ssafy.a302.domain.badge.repository.BadgeRepository;
import com.ssafy.a302.domain.badge.repository.MemberBadgeRepository;
import com.ssafy.a302.domain.badge.service.dto.BadgeDto;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final MemberRepository memberRepository;

    private final BadgeRepository badgeRepository;

    private final MemberBadgeRepository memberBadgeRepository;

    @Override
    public MemberDto.Profile getProfile(Long memberSeq) {
        MemberDto memberDto = memberRepository.findMemberDtoBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MEMBER_SEQ));
        List<BadgeDto.ForProfile> badgesForProfile = badgeRepository.findBadgesForProfileDto()
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.UNAVAILABLE));

        /**
         * 회원이 보유한 뱃지를 가져와서 전체 뱃지 목록에 보유 여부를 체크한다.
         */
        List<Badge> badgesByMember = memberBadgeRepository.findBadgeAllByMemberSeq(memberSeq)
                .orElse(null);
        if (badgesByMember != null) {
            for (BadgeDto.ForProfile badgeForProfile : badgesForProfile) {
                for (int i = badgesByMember.size() - 1; i >= 0; i--) {
                    Badge badge = badgesByMember.get(i);
                    if (badgeForProfile.getName().equals(badge.getName())) {
                        badgeForProfile.achieve();
                        badgesByMember.remove(i);
                    }
                }
                if (badgesByMember.isEmpty()) {
                    break;
                }
            }
        }

        /**
         * 프로필 DTO 반환
         */
        return memberDto.createProfile(badgesForProfile);
    }
}
