package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import com.ssafy.a302.domain.volunteer.repository.VolunteerCommentRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerCommentDto;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VolunteerCommentServiceImpl implements VolunteerCommentService{

    private final VolunteerCommentRepository volunteerCommentRepository;

    private final MemberRepository memberRepository;

    private final VolunteerRepository volunteerRepository;

    @Transactional
    @Override
    public Long registerVolunteerComment(Long volunteerSeq, Long memberSeq, VolunteerCommentDto.RegisterInfo registerInfo) {
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        VolunteerComment newVolunteerComment = VolunteerComment.builder()
                .volunteer(findVolunteer)
                .member(findMember)
                .content(registerInfo.getContent())
                .build();

        Long parentSeq = registerInfo.getParentSeq();
        if(parentSeq != null){
            VolunteerComment parentComment = volunteerCommentRepository.findBySeq(parentSeq)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

            newVolunteerComment.createParent(parentComment);
        }
        return volunteerCommentRepository.save(newVolunteerComment).getSeq();
    }

    @Transactional
    @Override
    public VolunteerComment deleteVolunteerComment(Long volunteerSeq, Long commentsSeq, Long memberSeq) {
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        VolunteerComment findVolunteerComment = volunteerCommentRepository.findById(commentsSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_COMMENT));

        if(findVolunteerComment.getMember().getSeq().equals(findMember.getSeq())){
            findVolunteerComment.delete();
        }else{
            throw new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_CREATOR_COMMENT);
        }

        return findVolunteerComment;

    }


}
