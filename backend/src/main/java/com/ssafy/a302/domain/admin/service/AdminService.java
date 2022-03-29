package com.ssafy.a302.domain.admin.service;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.adopt.service.dto.AdoptAuthDto;

public interface AdminService {

    VolunteerAuthDto.Response volunteerAuthDetail(Long volunteerSeq);

    void changeVolunteerAuthStatus(VolunteerAuthDto volunteerAuthDto, Long volunteerSeq);

    AdoptAuthDto.Response adoptAuthDetail(Long adoptSeq);
}
