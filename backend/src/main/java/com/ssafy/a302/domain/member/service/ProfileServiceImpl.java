package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.adopt.repository.AdoptAuthRepository;
import com.ssafy.a302.domain.badge.entity.Badge;
import com.ssafy.a302.domain.badge.repository.BadgeRepository;
import com.ssafy.a302.domain.badge.repository.MemberBadgeRepository;
import com.ssafy.a302.domain.badge.service.dto.BadgeDto;
import com.ssafy.a302.domain.community.repository.CommunityRepository;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.domain.member.service.dto.ProfileDto;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.repository.VolunteerParticipantRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private final CommunityRepository communityRepository;

    private final AdoptAuthRepository adoptAuthRepository;

    private final VolunteerRepository volunteerRepository;

    private final VolunteerParticipantRepository volunteerParticipantRepository;

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

    @Override
    public ProfileDto.CommunityPage getCommunities(Long memberSeq, Pageable pageable) {
        Integer totalCount = communityRepository.countAllByMemberSeq(memberSeq);
        Integer totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        List<ProfileDto.Community> communities = communityRepository.findCommunitiesForProfile(memberSeq, pageable)
                .orElse(null);

        return ProfileDto.CommunityPage.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .currentPageNumber(pageable.getPageNumber())
                .communities(communities)
                .build();
    }

    @Override
    public ProfileDto.AdoptPage getAdopts(Long memberSeq, Pageable pageable) {
        Integer totalCount = adoptAuthRepository.countAllByMemberSeq(memberSeq);
        Integer totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        List<ProfileDto.Adopt> adopts = adoptAuthRepository.findAdoptsForProfile(memberSeq, pageable)
                .orElse(null);

        return ProfileDto.AdoptPage.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .currentPageNumber(pageable.getPageNumber())
                .adopts(adopts)
                .build();
    }

    @Override
    public ProfileDto.VolunteerPage getVolunteers(Long memberSeq, Pageable pageable) {
        Integer totalCount = volunteerParticipantRepository.countAllByMemberSeq(memberSeq);
        Integer totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        List<ProfileDto.Volunteer> volunteers = volunteerRepository.findVolunteersForProfile(memberSeq, pageable)
                .orElse(null);

        for (ProfileDto.Volunteer volunteer : volunteers) {
            Volunteer findVolunteer = volunteerRepository.findBySeq(volunteer.getVolunteerSeq())
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

            List<ProfileDto.Volunteer.ParticipantInfo> participantInfos = new ArrayList<>();
            for (VolunteerParticipant volunteerParticipant : findVolunteer.getVolunteerParticipants()) {
                Member findMember = volunteerParticipant.getMember();
                participantInfos.add(ProfileDto.Volunteer.ParticipantInfo.builder()
                        .volunteerSeq(volunteer.getVolunteerSeq())
                        .isApprove(volunteerParticipant.getApprove())
                        .memberSeq(findMember.getSeq())
                        .memberNickname(findMember.getDetail().getNickname())
                        .build());
            }

            volunteer.changeParticipantInfos(participantInfos);
        }

        return ProfileDto.VolunteerPage.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .currentPageNumber(pageable.getPageNumber())
                .volunteers(volunteers)
                .build();
    }
}
