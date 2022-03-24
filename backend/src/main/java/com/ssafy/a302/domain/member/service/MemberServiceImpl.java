package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.entity.Member;
import com.ssafy.a302.domain.member.entity.MemberDetail;
import com.ssafy.a302.domain.member.exception.DuplicateEmailException;
import com.ssafy.a302.domain.member.exception.DuplicateNicknameException;
import com.ssafy.a302.domain.member.exception.DuplicateTelException;
import com.ssafy.a302.domain.member.repository.MemberDetailRepository;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.MemberDto;
import com.ssafy.a302.global.message.ErrorMessage;
import com.ssafy.a302.global.message.Message;
import com.ssafy.a302.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final MemberDetailRepository memberDetailRepository;

    private final PasswordEncoder passwordEncoder;

    private final FileUtil fileUtil;

    @Override
    public Member getMemberByEmail(String email) {
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
        } else if (memberDetailRepository.existsByTel(memberDto.getTel())) {
            throw new DuplicateTelException(Message.DUPLICATE_MEMBER_TEL);
        } else if (memberDto.getPassword().contains(memberDto.getEmail().split("@")[0])) {
            throw new IllegalArgumentException(Message.PASSWORD_CONTAIN_MEMBER_EMAIL);
        } else if (memberDto.getPassword().contains(memberDto.getNickname())) {
            throw new IllegalArgumentException(Message.PASSWORD_CONTAIN_MEMBER_NICKNAME);
        }

        Member member = memberDto.toEntity();
        member.changeRole(Member.Role.MEMBER);
        member.encryptPassword(passwordEncoder);
        memberDto.toDetailEntity(member);

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

    @Transactional
    @Override
    public MemberDto.Response modify(Long memberSeq, MemberDto modifyInfoDto) {
        Member originMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));
        MemberDetail originMemberDetail = originMember.getDetail();

        String newPassword = modifyInfoDto.getPassword();
        String newNickname = modifyInfoDto.getNickname();
        String newTel = modifyInfoDto.getTel();

        if (!originMemberDetail.getNickname().equals(newNickname) &&
                memberDetailRepository.existsByNickname(newNickname)) {
            /**
             * 전달 받은 닉네임이 기존 닉네임과 다른 상황에서 전달 받은 닉네임이 서버에 존재하는지 확인
             */
            throw new DuplicateNicknameException(Message.DUPLICATE_MEMBER_NICKNAME);
        } else if (!originMemberDetail.getTel().equals(newTel) &&
                memberDetailRepository.existsByTel(newTel)) {
            /**
             * 전달 받은 핸드폰 번호가 기존 핸드폰 번호와 다른 상황에서 전달 받은 핸드폰 번호가 서버에 존재하는지 확인
             */
            throw new DuplicateTelException(Message.DUPLICATE_MEMBER_TEL);
        } else if (StringUtils.hasText(newPassword)) {
            /**
             * 패스워드가 null 이 아니면 패스워드에 기존 이메일과 변경할 닉네임이 포함되어 있는지 확인
             */
            if (newPassword.contains(originMember.getEmail().split("@")[0])) {
                throw new IllegalArgumentException(Message.PASSWORD_CONTAIN_MEMBER_EMAIL);
            } else if (newPassword.contains(newNickname)) {
                throw new IllegalArgumentException(Message.PASSWORD_CONTAIN_MEMBER_NICKNAME);
            }

            originMember.changePassword(passwordEncoder.encode(newPassword));
        }

        originMemberDetail.modifyInfo(modifyInfoDto);

        return originMember.toResponseDto();
    }

    @Override
    public MemberDto.LoginResponse getMemberLoginResponseDto(String email) {
        Member findMember = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        return findMember.toLoginResponseDto();
    }

    @Override
    public Member getMemberBySeq(Long seq) {
        return memberRepository.findMemberBySeq(seq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));
    }

    @Transactional
    @Override
    public String modifyProfileImage(Long memberSeq, MultipartFile profileImageFile) throws IOException {
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MEMBER_SEQ));

        MemberDetail memberDetail = findMember.getDetail();

        String storeFilename = memberDetail.getProfileImageFilename();
        if (storeFilename != null) {
            fileUtil.removeProfileImage(storeFilename);
        }

        try {
            storeFilename = fileUtil.storeFile(profileImageFile);
            if (storeFilename == null) {
                throw new IOException();
            }
        } catch (IOException e) {
            throw new IOException(ErrorMessage.UNAVAILABLE);
        }

        memberDetail.changeProfileImageFilename(storeFilename);

        return "static/images/profile/" + storeFilename;
    }

    @Transactional
    @Override
    public void removeProfileImage(Long memberSeq) throws IOException {
        Member findMember = memberRepository.findMemberBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_MEMBER_SEQ));

        MemberDetail memberDetail = findMember.getDetail();

        if (memberDetail.getProfileImageFilename() == null) {
            throw new IllegalArgumentException(ErrorMessage.BAD_REQUEST);
        }

        String removedFilename = memberDetail.clearProfileImageFilename();

        fileUtil.removeProfileImage(removedFilename);
    }
}
