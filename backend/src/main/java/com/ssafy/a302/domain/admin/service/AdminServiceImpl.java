package com.ssafy.a302.domain.admin.service;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.adopt.entity.AdoptAuth;
import com.ssafy.a302.domain.adopt.repository.AdoptAuthRepository;
import com.ssafy.a302.domain.adopt.service.dto.AdoptAuthDto;
import com.ssafy.a302.domain.volunteer.entity.VolunteerAuth;
import com.ssafy.a302.domain.volunteer.repository.VolunteerAuthRepository;
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
}
