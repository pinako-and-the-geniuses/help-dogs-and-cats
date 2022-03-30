package com.ssafy.a302.domain.admin.service;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.adopt.service.dto.AdoptAuthDto;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    VolunteerAuthDto.Response volunteerAuthDetail(Long volunteerSeq);

    void changeVolunteerAuthStatus(VolunteerAuthDto volunteerAuthDto, Long volunteerSeq);

    AdoptAuthDto.Response adoptAuthDetail(Long adoptSeq);

    void changeAdoptAuthStatus(AdoptAuthDto adoptAuthDto, Long adoptSeq);

    VolunteerAuthDto.VolunteerAuthPage getVolunteerAuthList(Pageable pageable, String search);

    AdoptAuthDto.AdoptAuthPage getAdoptAuthList(Pageable pageable, String search);
}
