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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService{

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
}
