package com.ssafy.a302.domain.admin.service;

import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.volunteer.entity.VolunteerAuth;

public interface AdminService {
    VolunteerAuthDto.Response volunteerAuthDetail(Long volunteerSeq);
}
