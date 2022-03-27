package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.repository.VolunteerParticipantRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private final MemberRepository memberRepository;

    private final VolunteerParticipantRepository volunteerParticipantRepository;

    @Transactional
    @Override
    public VolunteerDto.Response register(VolunteerDto volunteerDto, Long memberSeq) {
        // exception 조건들 추가

        Volunteer volunteer = volunteerDto.toEntity();

        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        volunteer.setCreator(findMember);

        Volunteer savedVolunteer = volunteerRepository.save(volunteer);

        VolunteerParticipant volunteerParticipant = new VolunteerParticipant(savedVolunteer, findMember);
        volunteerParticipantRepository.save(volunteerParticipant);

        return savedVolunteer.toResponseDto();
    }

    @Transactional
    @Override
    public Volunteer deleteVolunteer(Long volunteerSeq, Long memberSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        if(findVolunteer.getMember().getSeq().equals(memberSeq)){
            findVolunteer.delete();
        }else{
            throw new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER);
        }

        return findVolunteer;
    }

    @Transactional
    @Override
    public Volunteer changeVolunteerStatus(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        if(findVolunteer.getMember().getSeq().equals(memberSeq)){
            findVolunteer.changeVolunteerStatus(volunteerDto.getStatus());
        }else{
            throw new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER);
        }
        return findVolunteer;
    }

    @Override
    public VolunteerDto.VolunteerListPage getPage(Pageable pageable, String keyword) {
        Integer totalCount = volunteerRepository.countAllByKeyword(keyword);
        Integer totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        List<VolunteerDto.ForPage> volunteersForPage = volunteerRepository.findVolunteersForPage(pageable, keyword)
                .orElse(null);

        return VolunteerDto.VolunteerListPage.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .volunteersForPage(volunteersForPage)
                .currentPageNumber(pageable.getPageNumber())
                .build();
    }


}
