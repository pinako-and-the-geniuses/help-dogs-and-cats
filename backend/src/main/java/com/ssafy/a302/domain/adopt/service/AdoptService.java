package com.ssafy.a302.domain.adopt.service;

import com.ssafy.a302.domain.adopt.service.dto.AdoptDto;

public interface AdoptService {

    Long requestAdoptAuth(Long memberSeq, AdoptDto.AdoptAuth adoptAuth);

    Long modifyAdoptAuth(Long memberSeq, Long adoptAuthSeq, AdoptDto.AdoptAuth adoptAuth);
}
