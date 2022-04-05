package com.ssafy.a302.domain.admin.service;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.adopt.entity.AdoptAuth;
import com.ssafy.a302.domain.adopt.repository.AdoptAuthRepository;
import com.ssafy.a302.domain.adopt.service.dto.AdoptAuthDto;
import com.ssafy.a302.domain.volunteer.entity.VolunteerAuth;
import com.ssafy.a302.domain.volunteer.entity.VolunteerParticipant;
import com.ssafy.a302.domain.volunteer.repository.VolunteerAuthRepository;
import com.ssafy.a302.global.constant.ErrorMessage;
import com.ssafy.a302.global.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {

    private final VolunteerAuthRepository volunteerAuthRepository;

    private final AdoptAuthRepository adoptAuthRepository;

    @Override
    public VolunteerAuthDto.Response volunteerAuthDetail(Long volunteerSeq) {

        VolunteerAuth findVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_AUTH));

        return findVolunteerAuth.toResponseDto();
    }

    @Transactional
    @Override
    public void changeVolunteerAuthStatus(VolunteerAuthDto volunteerAuthDto, Long volunteerSeq) {
        VolunteerAuth findVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_AUTH));

        findVolunteerAuth.changeVolunteerAuthStatus(volunteerAuthDto.getStatus());

        if (volunteerAuthDto.getStatus() == Status.DONE) {
            for (VolunteerParticipant volunteerParticipant : findVolunteerAuth.getVolunteer().getVolunteerParticipants()) {
                if (volunteerParticipant.getApprove()) {
                    volunteerParticipant.getMember().getDetail().incrementExp(40);
                }
            }
        }
    }

    @Override
    public AdoptAuthDto.Response adoptAuthDetail(Long adoptSeq) {

        AdoptAuth findAdoptAuth = adoptAuthRepository.findBySeq(adoptSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_ADOPT_AUTH));

        return findAdoptAuth.toResponseDto();
    }

    @Transactional
    @Override
    public void changeAdoptAuthStatus(AdoptAuthDto adoptAuthDto, Long adoptSeq) {
        AdoptAuth findAdoptAuth = adoptAuthRepository.findBySeq(adoptSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_ADOPT_AUTH));

        findAdoptAuth.changeAdoptAuthStatus(adoptAuthDto.getStatus());

        if (adoptAuthDto.getStatus() == Status.DONE) {
            findAdoptAuth.getMember().getDetail().incrementExp(100);
        }
    }

    @Override
    public VolunteerAuthDto.VolunteerAuthPage getVolunteerAuthList(Pageable pageable, String search) {
        Integer totalCount = volunteerAuthRepository.countAllByStatus(search);
        Integer totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        List<VolunteerAuthDto.VolunteerAuthPage.VolunteerAuthForPage> volunteerAuthForPages = volunteerAuthRepository.findVolunteerAuthForPageDto(pageable, search)
                .orElse(null);

        return VolunteerAuthDto.VolunteerAuthPage.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .volunteerAuthForPages(volunteerAuthForPages)
                .currentPageNumber(pageable.getPageNumber())
                .build();
    }

    @Override
    public AdoptAuthDto.AdoptAuthPage getAdoptAuthList(Pageable pageable, String search) {
        Integer totalCount = adoptAuthRepository.countAllByStatus(search);
        Integer totalPageNumber = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        List<AdoptAuthDto.AdoptAuthPage.AdoptAuthForPage> adoptAuthForPages = adoptAuthRepository.findAdoptAuthForPageDto(pageable, search)
                .orElse(null);

        return AdoptAuthDto.AdoptAuthPage.builder()
                .totalCount(totalCount)
                .totalPageNumber(totalPageNumber)
                .adoptAuthForPages(adoptAuthForPages)
                .currentPageNumber(pageable.getPageNumber())
                .build();
    }

    @Override
    public Map<String, Integer> getAuthRequestCount() {
        Map<String, Integer> authRequestCount = new HashMap<>();

        authRequestCount.put("volunteerAuthCount", volunteerAuthRepository.countAllByStatusEqRequest());
        authRequestCount.put("adoptAuthCount", adoptAuthRepository.countAllByStatusEqRequest());

        return authRequestCount;
    }
}
