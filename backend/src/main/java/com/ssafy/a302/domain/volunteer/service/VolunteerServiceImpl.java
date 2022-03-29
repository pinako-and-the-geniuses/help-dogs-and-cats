package com.ssafy.a302.domain.volunteer.service;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.volunteer.entity.Volunteer;
import com.ssafy.a302.domain.volunteer.entity.VolunteerAuth;
import com.ssafy.a302.domain.volunteer.entity.VolunteerComment;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.repository.VolunteerAuthRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerCommentRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerParticipantRepository;
import com.ssafy.a302.domain.volunteer.repository.VolunteerRepository;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerCommentDto;
import com.ssafy.a302.domain.volunteer.service.dto.VolunteerDto;
import com.ssafy.a302.global.constant.ErrorMessage;
import com.ssafy.a302.global.enums.Status;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private final MemberRepository memberRepository;

    private final VolunteerParticipantRepository volunteerParticipantRepository;

    private final VolunteerCommentRepository volunteerCommentRepository;

    private final VolunteerAuthRepository volunteerAuthRepository;

    @Transactional
    @Override
    public Long register(VolunteerDto volunteerDto, Long memberSeq) {

        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        Volunteer volunteer = volunteerDto.toEntity(findMember);
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);

        VolunteerParticipant volunteerParticipant = new VolunteerParticipant(savedVolunteer, findMember);
        volunteerParticipant.changeParticipantIsApprove(true);

        volunteerParticipantRepository.save(volunteerParticipant);

        return savedVolunteer.getSeq();
    }

    @Transactional
    @Override
    public Long updateVolunteerDetail(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        if (!findVolunteer.getMember().getSeq().equals(memberSeq)){
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }
            findVolunteer.modify(volunteerDto);
            return findVolunteer.getSeq();
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
    @Transactional
    @Override
    public VolunteerDto.Detail volunteerDetail(Long volunteerSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        findVolunteer.incrementViewCount();

        Member writer = findVolunteer.getMember();
        List<VolunteerComment> volunteerComments = volunteerCommentRepository.findCommentsByVolunteerSeq(volunteerSeq)
                .orElse(null);


        List<VolunteerCommentDto.ForDetail> commentsForDetail = convertNestedStructure((volunteerComments));

        return VolunteerDto.Detail.create(findVolunteer, writer, commentsForDetail);
    }

    private List<VolunteerCommentDto.ForDetail> convertNestedStructure(List<VolunteerComment> comments) {
        if (comments == null) return null;

        List<VolunteerCommentDto.ForDetail> result = new ArrayList<>();
        Map<Long, VolunteerCommentDto.ForDetail> map = new HashMap<>();
        comments.forEach(comment -> {
            VolunteerCommentDto.ForDetail forDetailDto = comment.toForDetailDto();
            map.put(forDetailDto.getCommentSeq(), forDetailDto);
            if (comment.getParent() != null) {
                map.get(comment.getParent().getSeq()).getChildren().add(forDetailDto);
            } else {
                result.add(forDetailDto);
            }
        });
        return result;
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

    @Transactional
    @Override
    public Long requestVolunteerAuth(Long memberSeq, Long volunteerSeq, VolunteerDto.VolunteerAuth volunteerAuth) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        /**
         * 모집한 사람이 아닐 경우
         */
        if (!findVolunteer.getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }

        /**
         * 이미 인증 요청한 경우
         */
        if (volunteerAuthRepository.existsByVolunteerSeq(volunteerSeq)) {
            throw new IllegalArgumentException(ErrorMessage.BAD_REQUEST);
        }

        /**
         * 봉사활동 인증 데이터 저장
         */
        VolunteerAuth savedVolunteerAuth = volunteerAuthRepository.save(VolunteerAuth.builder()
                .volunteer(findVolunteer)
                .content(volunteerAuth.getContent())
                .build());

        /**
         * 봉사활동 seq 로 봉사활동 참여자 엔티티 리스트 조회
         */
        List<VolunteerParticipant> volunteerParticipants = volunteerParticipantRepository.findVolunteerParticipantBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        /**
         * 전달 받은 인증할 회원의 seq 리스트
         */
        List<Long> authenticatedParticipantSequences = volunteerAuth.getAuthenticatedParticipantSequences();

        /**
         * 전달 받은 회원 seq 를 제외하고 모두 false 처리
         */
        for (VolunteerParticipant volunteerParticipant : volunteerParticipants) {
            volunteerParticipant.changeParticipantIsApprove(false);
            for (Long authenticatedParticipantSequence : authenticatedParticipantSequences) {
                if (volunteerParticipant.getMember().getSeq().equals(authenticatedParticipantSequence)) {
                    volunteerParticipant.changeParticipantIsApprove(true);
                }
            }
        }

        return savedVolunteerAuth.getSeq();
    }

    @Transactional
    @Override
    public Long modifyVolunteerAuth(Long memberSeq, Long volunteerSeq, VolunteerDto.VolunteerAuth volunteerAuth) {
        VolunteerAuth findVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        if (!findVolunteerAuth.getVolunteer().getMember().getSeq().equals(memberSeq)) {
            throw new IllegalArgumentException(ErrorMessage.FORBIDDEN);
        }

        if (!findVolunteerAuth.getStatus().equals(Status.REJECT)) {
            throw new IllegalArgumentException(ErrorMessage.BAD_REQUEST);
        }

        findVolunteerAuth.modify(volunteerAuth);

        /**
         * 봉사활동 seq 로 봉사활동 참여자 엔티티 리스트 조회
         */
        List<VolunteerParticipant> volunteerParticipants = volunteerParticipantRepository.findVolunteerParticipantBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        /**
         * 전달 받은 인증할 회원의 seq 리스트
         */
        List<Long> authenticatedParticipantSequences = volunteerAuth.getAuthenticatedParticipantSequences();

        /**
         * 전달 받은 회원 seq 를 제외하고 모두 false 처리
         */
        for (VolunteerParticipant volunteerParticipant : volunteerParticipants) {
            volunteerParticipant.changeParticipantIsApprove(false);
            for (Long authenticatedParticipantSequence : authenticatedParticipantSequences) {
                if (volunteerParticipant.getMember().getSeq().equals(authenticatedParticipantSequence)) {
                    volunteerParticipant.changeParticipantIsApprove(true);
                }
            }
        }

        return findVolunteerAuth.getSeq();
    }

    @Override
    public VolunteerDto.VolunteerAuthDetail getVolunteerAuth(Long memberSeq, Long volunteerSeq) {
        VolunteerAuth findVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        if (!findVolunteerAuth.getVolunteer().getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }

        List<VolunteerDto.VolunteerAuthDetail.Participant> participants =
                volunteerAuthRepository.findVolunteerParticipantsDtoByVolunteerSeq(volunteerSeq)
                        .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        return VolunteerDto.VolunteerAuthDetail.builder()
                .volunteerSeq(findVolunteerAuth.getSeq())
                .content(findVolunteerAuth.getContent())
                .participants(participants)
                .build();
    }
}
