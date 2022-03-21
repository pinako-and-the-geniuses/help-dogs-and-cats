package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.exception.DuplicateEmailException;
import com.ssafy.a302.domain.member.exception.DuplicateNicknameException;
import com.ssafy.a302.domain.member.repository.MemberDetailRepository;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.global.message.ErrorMessage;
import com.ssafy.a302.global.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final MemberDetailRepository memberDetailRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));
    }

    @Transactional
    @Override
    public MemberDto.Response register(MemberDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            throw new DuplicateEmailException(Message.DUPLICATE_MEMBER_EMAIL);
        } else if (memberDetailRepository.existsByNickname(memberDto.getNickname())) {
            throw new DuplicateNicknameException(Message.DUPLICATE_MEMBER_NICKNAME);
        } else if (memberDto.getPassword().contains(memberDto.getEmail().split("@")[0])) {
            throw new IllegalArgumentException(Message.PASSWORD_CONTAIN_MEMBER_EMAIL);
        } else if (memberDto.getPassword().contains(memberDto.getNickname())) {
            throw new IllegalArgumentException(Message.PASSWORD_CONTAIN_MEMBER_NICKNAME);
        }

        Member member = memberDto.toEntity();
        member.changeRole(Member.Role.MEMBER);
        member.encryptPassword(passwordEncoder);
        MemberDetail detail = memberDto.toDetailEntity(member);

        Member savedMember = memberRepository.save(member);

        return savedMember.toResponseDto();
    }

    @Override
    public boolean isExistsEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean isExistsNickname(String nickname) {
        return memberDetailRepository.existsByNickname(nickname);
    }

    @Override
    public boolean login(MemberDto memberDto) {
        Member findMember = memberRepository.findMemberByEmail(memberDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        return passwordEncoder.matches(memberDto.getPassword(), findMember.getPassword());
    }

    @Override
    public MemberDto.LoginResponse getMemberLoginResponseDto(String email) {
        Member findMember = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        return findMember.toLoginResponseDto();
    }
}
