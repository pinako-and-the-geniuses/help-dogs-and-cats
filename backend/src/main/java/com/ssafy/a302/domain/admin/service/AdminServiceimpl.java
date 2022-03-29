package com.ssafy.a302.domain.admin.service;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
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
public class AdminServiceimpl implements AdminService{

    private final VolunteerAuthRepository volunteerAuthRepository;


    @Override
    public VolunteerAuthDto.Response volunteerAuthDetail(Long volunteerSeq) {

        VolunteerAuth findVolunteerAuth = volunteerAuthRepository.findByVolunteerSeq(volunteerSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VOLUNTEER_AUTH));

        findVolunteerAuth.getLastModifiedDate();
        return findVolunteerAuth.toResponseDto();
    }
}