package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.repository.VolunteerParticipantRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerParticipantDto;
import com.ssafy.a302.global.constant.ErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VolunteerParticipantServiceImpl implements VolunteerParticipantService {

    private final VolunteerRepository volunteerRepository;

    private final MemberRepository memberRepository;

    private final VolunteerParticipantRepository volunteerParticipantRepository;

    @Transactional
    @Override
    public VolunteerParticipantDto.Response applyVolunteer(Long volunteerSeq, Long memberSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        Optional<VolunteerParticipant> findVolunteerParticipant = volunteerParticipantRepository.findByMemberSeqAndVolunteerSeq(memberSeq, volunteerSeq);

        if (findVolunteerParticipant.isPresent()){
            throw new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_PARTICIPANT);
        }else{
            VolunteerParticipant volunteerParticipant = new VolunteerParticipant(findVolunteer, findMember);
            volunteerParticipantRepository.save(volunteerParticipant);
            return volunteerParticipant.toResponseDto();
        }

    }

    @Transactional
    @Override
    public void cancelVolunteer(Long volunteerSeq, Long memberSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        VolunteerParticipant findVolunteerParticipant = volunteerParticipantRepository.findByMemberSeqAndVolunteerSeq(memberSeq, volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_CANCEL_PARTICIPANT));

        volunteerParticipantRepository.delete(findVolunteerParticipant);
    }

    @Override
    public List<SimpleVolunteerParticipantDto> getVolunteerParticipantList(Long volunteerSeq, Long memberSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        VolunteerParticipant findVolunteerParticipant = volunteerParticipantRepository.findByMemberSeqAndVolunteerSeq(memberSeq, volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_CANCEL_PARTICIPANT));

        List<VolunteerParticipant> results = volunteerParticipantRepository.findVolunteerParticipantBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        List<SimpleVolunteerParticipantDto> result = results.stream()
                .map(res -> new SimpleVolunteerParticipantDto(res))
                .collect(toList());

        return result;
    }

    @Getter
    static class SimpleVolunteerParticipantDto {
        private Long seq;

        private String email;

        private String nickname;

        private boolean isApprove;

        public SimpleVolunteerParticipantDto(VolunteerParticipant volunteerParticipant) {
            seq = volunteerParticipant.getMember().getSeq();
            email = volunteerParticipant.getMember().getEmail();
            nickname = volunteerParticipant.getMember().getDetail().getNickname();
            isApprove = volunteerParticipant.getApprove();
        }
    }

    @Transactional
    @Override
    public VolunteerParticipant changeParticipantIsApprove(VolunteerParticipantDto volunteerParticipantDto,Long volunteerSeq, Long memberSeq, Long memberCreatorSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        if(findVolunteer.getMember().getSeq().equals(memberCreatorSeq)){
            VolunteerParticipant findVolunteerParticipant = volunteerParticipantRepository.findByMemberSeqAndVolunteerSeq(memberSeq, volunteerSeq)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_CANCEL_PARTICIPANT));

            findVolunteerParticipant.changeParticipantIsApprove(volunteerParticipantDto.getApprove());
            return findVolunteerParticipant;
        }else{
            // 봉사활동 개설자와 현재 참여자들의 참석여부를 수정하려는 회원이 다른 경우
            throw new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_CREATOR_PARTICIPANT);
        }

    }

    @Transactional
    @Override
    public VolunteerParticipant deleteVolunteerParticipant(Long volunteerSeq, Long memberSeq, Long memberCreatorSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        if(findVolunteer.getMember().getSeq().equals(memberCreatorSeq)) {
            VolunteerParticipant findVolunteerParticipant = volunteerParticipantRepository.findByMemberSeqAndVolunteerSeq(memberSeq, volunteerSeq)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_CANCEL_PARTICIPANT));
            volunteerParticipantRepository.delete(findVolunteerParticipant);
            return null;
        }else{
            throw new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_CREATOR_PARTICIPANT);
        }
    }

    @Override
    public List<Long> getParticipantSeqALlByVolunteerSeqAndApproveEqTrue(Long volunteerSeq) {
        return volunteerParticipantRepository.findParticipantSeqAllByVolunteerSeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));
    }
}
