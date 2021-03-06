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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        findMember.getDetail().incrementExp(5);

        return savedVolunteer.getSeq();
    }

    @Transactional
    @Override
    public Long updateVolunteerDetail(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        if (!findVolunteer.getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }
        findVolunteer.modify(volunteerDto);
        return findVolunteer.getSeq();
    }


    @Transactional
    @Override
    public void remove(Long volunteerSeq, Long memberSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        if (!findVolunteer.getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }

        findVolunteer.delete();

        findVolunteer.getMember().getDetail().decrementExp(5);
    }

    @Transactional
    @Override
    public Volunteer changeVolunteerStatus(VolunteerDto volunteerDto, Long volunteerSeq, Long memberSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));

        if (findVolunteer.getMember().getSeq().equals(memberSeq)) {
            findVolunteer.changeVolunteerStatus(volunteerDto.getStatus());
        } else {
            throw new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER);
        }
        return findVolunteer;
    }

    // ???????????? ?????? ?????? ?????????
    @Transactional
    @Override
    public VolunteerDto.Detail volunteerDetail(Long volunteerSeq) {
        Volunteer findVolunteer = volunteerRepository.findBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER));
        findVolunteer.incrementViewCount();

        Member writer = findVolunteer.getMember();

        List<VolunteerParticipant> findVolunteerParticipants = volunteerParticipantRepository.findAllByVolunteerSeq(volunteerSeq)
                .orElse(null);
        int approvedCount = findVolunteerParticipants != null ? findVolunteerParticipants.size() : 0;

        List<VolunteerComment> volunteerComments = volunteerCommentRepository.findCommentsByVolunteerSeq(volunteerSeq)
                .orElse(null);
        List<VolunteerCommentDto.ForDetail> commentsForDetail = convertNestedStructure(volunteerComments);

        return VolunteerDto.Detail.create(findVolunteer, writer, approvedCount, commentsForDetail);
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

    @Override
    public VolunteerDto.VolunteerListPage getPage(Pageable pageable, VolunteerDto.SearchInfo searchInfo) {
        Integer totalCount = volunteerRepository.countAllBySearchInfo(searchInfo);
        Integer totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        List<VolunteerDto.ForPage> volunteersForPage = volunteerRepository.findVolunteersForPage(pageable, searchInfo)
                .orElse(null);

        if (volunteersForPage != null) {
            for (VolunteerDto.ForPage forPage : volunteersForPage) {
                forPage.addApproveCount(volunteerParticipantRepository.countAllByVolunteerSeqAndApproveEqTrue(forPage.getSeq()));
            }
        }

        return VolunteerDto.VolunteerListPage.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .volunteersForPage(volunteersForPage)
                .currentPageNumber(pageable.getPageNumber())
                .build();
    }

    @Override
    public VolunteerDto.VolunteerListPage getPageAll(Pageable pageable) {
        Integer totalCount = volunteerRepository.countAll();
        Integer totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        List<VolunteerDto.ForPage> volunteersForPage = volunteerRepository.findVolunteersForPageAll(pageable)
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
         * ????????? ????????? ?????? ??????
         */
        if (!findVolunteer.getMember().getSeq().equals(memberSeq)) {
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        }

        /**
         * ?????? ?????? ????????? ??????
         */
        if (volunteerAuthRepository.existsByVolunteerSeq(volunteerSeq)) {
            throw new IllegalArgumentException(ErrorMessage.BAD_REQUEST);
        }

        /**
         * ???????????? ?????? ????????? ??????
         */
        VolunteerAuth savedVolunteerAuth = volunteerAuthRepository.save(VolunteerAuth.builder()
                .volunteer(findVolunteer)
                .content(volunteerAuth.getContent())
                .build());

        /**
         * ???????????? seq ??? ???????????? ????????? ????????? ????????? ??????
         */
        List<VolunteerParticipant> volunteerParticipants = volunteerParticipantRepository.findVolunteerParticipantBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        /**
         * ?????? ?????? ????????? ????????? seq ?????????
         */
        List<Long> authenticatedParticipantSequences = volunteerAuth.getAuthenticatedParticipantSequences();

        /**
         * ?????? ?????? ?????? seq ??? ???????????? ?????? false ??????
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
         * ???????????? seq ??? ???????????? ????????? ????????? ????????? ??????
         */
        List<VolunteerParticipant> volunteerParticipants = volunteerParticipantRepository.findVolunteerParticipantBySeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));

        /**
         * ?????? ?????? ????????? ????????? seq ?????????
         */
        List<Long> authenticatedParticipantSequences = volunteerAuth.getAuthenticatedParticipantSequences();

        /**
         * ?????? ?????? ?????? seq ??? ???????????? ?????? false ??????
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

    @Override
    public Member getMemberByVolunteerSeq(Long volunteerSeq) {
        return volunteerRepository.findMemberByVolunteerSeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BAD_REQUEST));
    }
}
