package com.ssafy.a302.domain.member.service;

import com.ssafy.a302.domain.member.entity.EmailAuth;
import com.ssafy.a302.domain.member.repository.EmailAuthRepository;
import com.ssafy.a302.domain.member.service.dto.EmailAuthVerifyServiceDto;
import com.ssafy.a302.global.message.ErrorMessage;
import com.ssafy.a302.global.message.Message;
import com.ssafy.a302.global.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmailServiceImpl implements EmailService {

    private final EntityManager em;

    private final EmailAuthRepository emailAuthRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private static String sender;

    @Override
    @Transactional
    public void sendEmailAuthKey(String email) {
        String authKey = UUID.randomUUID().toString();

        emailAuthRepository.save(new EmailAuth(email, authKey));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(sender);
        message.setSubject("도와주개냥 이메일 인증키입니다.");
        message.setText("안녕하세요 도와주개냥입니다.\n" +
                "이메일 인증키는 다음과 같습니다.\n" +
                "============================================\n" +
                "인증키 : " + authKey +
                "\n============================================\n" +
                "감사합니다.");
        javaMailSender.send(message);
    }

    @Override
    @Transactional
    public void verifyEmail(EmailAuthVerifyServiceDto emailAuthVerifyServiceDto) {
        EmailAuth emailAuth = emailAuthRepository
                .findEmailAuthByEmailAndAuthKey(emailAuthVerifyServiceDto.getEmail(),
                        emailAuthVerifyServiceDto.getAuthKey())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_EMAIL_AUTH));

        LocalDateTime curDateTime = LocalDateTime.now();

        if (ChronoUnit.MINUTES.between(emailAuth.getCreatedDate(), curDateTime) >= 5) {
            throw new IllegalArgumentException(Message.FAIL_AUTHENTICATE_EMAIL);
        }
    }

    @Override
    public boolean isExistsEmailAndAuthKey(String email, String authKey) {
        return emailAuthRepository.existsEmailAuthByEmailAndAuthKey(email, authKey);
    }

    @Override
    public void sendPasswordResetMail(String email) {
        String jwtToken = JwtTokenUtil.getToken(email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(sender);
        message.setSubject("도와주개냥 비밀번호 재설정 링크입니다.");
        message.setText("안녕하세요 도와주개냥입니다.\n" +
                "비밀번호 재설정 링크는 다음과 같습니다.\n" +
                "============================================\n" +
                "주소 : " + "http://localhost:3000/user/resetpwd/" + jwtToken +
                "\n============================================\n" +
                "감사합니다.");
        javaMailSender.send(message);
    }
}
