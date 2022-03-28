package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.community.service.dto.CommunityDto;
import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.volunteer.controller.VolunteerController;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.repository.VolunteerCommentRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerParticipantRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private final MemberRepository memberRepository;

    private final VolunteerParticipantRepository volunteerParticipantRepository;

    private final VolunteerCommentRepository volunteerCommentRepository;

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
    public VolunteerDto.Response updateVolunteerDetail(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq) {
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        if (findVolunteer.getMember().getSeq().equals(findMember.getSeq())){
            // 수정
            if (volunteerDto.getTitle() != null){
                findVolunteer.updateTitle(volunteerDto.getTitle());
            }
            if (volunteerDto.getContent() != null){
                findVolunteer.updateContent(volunteerDto.getContent());
            }
//            if (volunteerDto.getCategory() != null){
//                findVolunteer.updateCategory(volunteerDto.getCategory());
//            }
            if (volunteerDto.getActivityArea() != null){
                findVolunteer.updateActivityArea(volunteerDto.getActivityArea());
            }
            if (volunteerDto.getAuthTime() != null){
                findVolunteer.updateAuthTime(volunteerDto.getAuthTime());
            }
            if (volunteerDto.getContact() != null){
                findVolunteer.updateContact(volunteerDto.getContact());
            }
            if (volunteerDto.getEndDate() != null){
                findVolunteer.updateEndDate(volunteerDto.getEndDate());
            }
            if (volunteerDto.getMinParticipantCount() != null){
                findVolunteer.updateMinParticipantCount(volunteerDto.getMinParticipantCount());
            }
            if (volunteerDto.getMaxParticipantCount() != null){
                findVolunteer.updateMaxParticipantCount(volunteerDto.getMaxParticipantCount());
            }
            return findVolunteer.toResponseDto();

        }else {
            // 수정 못함
            throw new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_CREATOR);
        }

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

    // 봉사활동 상세 조회 페이지
    @Override
    public VolunteerDto.DetailResponse volunteerDetail(Long volunteerSeq, Long memberSeq) {
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        List<SimpleVolunteerCommentDto> result = findVolunteer.getVolunteerComments().stream()
                .map(res -> new SimpleVolunteerCommentDto(res))
                .collect(toList());

        VolunteerDto.DetailResponse toResponseDetailDto = findVolunteer.toResponseDetailDto();

        toResponseDetailDto.setVolunteerComment(result);

        return toResponseDetailDto;
    }
    @Data
    public static class SimpleVolunteerCommentDto {
        private Long seq;
        private String content;
        private LocalDateTime createdDate;
        private String nickname;
        private Long memberSeq;
        public SimpleVolunteerCommentDto(VolunteerComment volunteerComment) {
            seq = volunteerComment.getSeq();
            content = volunteerComment.getContent();
            createdDate = volunteerComment.getCreatedDate();
            nickname = volunteerComment.getMember().getDetail().getNickname();
            memberSeq = volunteerComment.getMember().getSeq();
        }
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
