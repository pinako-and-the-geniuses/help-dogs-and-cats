package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {

    Member getMemberByEmail(String email);

    MemberDto.Response register(MemberDto memberDto);

    boolean isExistsEmail(String email);

    boolean isExistsNickname(String nickname);

    boolean login(MemberDto memberDto);

    MemberDto.LoginResponse getMemberLoginResponseDto(String email);

    MemberDto.Response modify(Long memberSeq, MemberDto modifyInfoDto);

    Member getMemberBySeq(Long seq);

    String modifyProfileImage(Long memberSeq, MultipartFile profileImageFile) throws IOException;

    void removeProfileImage(Long memberSeq) throws IOException;
}
